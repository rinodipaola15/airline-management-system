FROM maven:3-jdk-11 as builder
WORKDIR /app
COPY . .
RUN mvn package -P production

FROM openjdk:11
WORKDIR /app
COPY --from=builder /app/target/user-service-0.0.1-SNAPSHOT.jar ./user-service.jar
CMD java -jar user-service.jar