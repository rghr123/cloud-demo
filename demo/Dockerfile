# 使用官方的 Java 开发镜像作为基础镜像
FROM maven:3.8.1-openjdk-17-slim AS build

# 设置工作目录
WORKDIR /app

# 将 pom.xml 和 src 目录复制到 Docker 镜像中
COPY pom.xml .
COPY src ./src

# 使用 Maven 构建应用
RUN mvn -f pom.xml clean package

# 使用官方的 Java 运行时镜像作为基础镜像
FROM openjdk:17-slim

# 将构建的 JAR 文件复制到 Docker 镜像中
COPY --from=build /app/target/*.jar app.jar

# 暴露端口
EXPOSE 8080

# 设置启动命令
ENTRYPOINT ["java","-jar","/app.jar"]