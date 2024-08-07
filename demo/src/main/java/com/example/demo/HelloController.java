package com.example.demo;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    private final Bucket bucket;
    private final MeterRegistry meterRegistry;

    public HelloController(MeterRegistry meterRegistry) {
        Bandwidth limit = Bandwidth.classic(100, Refill.intervally(100, Duration.ofSeconds(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
        this.meterRegistry = meterRegistry;
    }

    @GetMapping("/hello")
    @Timed(value = "hello.endpoint.requests", description = "Time taken to respond to hello endpoint")
    public ResponseEntity<Map<String, String>> sayHello() {
        if (bucket.tryConsume(1)) {
            Map<String, String> response = new HashMap<>();
            response.put("msg", "hello");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }
}
