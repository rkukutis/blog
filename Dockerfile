FROM node:20-alpine as frontend-builder
WORKDIR ./
COPY ./frontend/package.json .
RUN npm i
COPY ./frontend .
RUN npm run build

FROM maven:3.8.4-openjdk-17 as backend-builder
COPY backend/src /app/src
COPY backend/pom.xml /app
COPY --from=frontend-builder ./dist /app/src/main/resources/static
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:17-alpine
COPY --from=backend-builder app/target/*.jar /blog-app/app.jar
WORKDIR /blog-app
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]