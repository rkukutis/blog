
FROM eclipse-temurin:21-jdk-jammy
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
CMD ["java","-jar","app.jar"]