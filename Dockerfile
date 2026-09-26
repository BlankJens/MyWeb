FROM node:20-alpine AS frontend-build
WORKDIR /frontend
COPY mywindow/package*.json ./
RUN npm install
COPY mywindow/ ./
RUN npm run build

FROM maven:3.9.6-eclipse-temurin-21 AS backend-build
WORKDIR /app
COPY . .
RUN rm -rf ./src/main/resources/static/*
COPY --from=frontend-build /frontend/dist ./src/main/resources/static/

RUN mvn clean package -DskipTests

FROM openjdk:22-jdk
WORKDIR /app
COPY --from=backend-build /app/target/*.jar MyWeb.jar
ENTRYPOINT [ "java", "-jar", "MyWeb.jar" ]
