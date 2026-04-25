# 1. build stage
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app
COPY . .

RUN gradle clean build -x test --no-daemon

# 2. run stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/guessit-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.address=0.0.0.0 --server.port=${PORT:-8080}"]