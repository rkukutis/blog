
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve
EXPOSE 8080

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]