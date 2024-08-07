package configure;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> prometheusMetrics() {
        return registry -> {
            registry.config().commonTags("application", "your-application-name");
        };
    }
}
