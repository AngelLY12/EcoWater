# Etapa 1: Build - compila el proyecto y genera el JAR
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar archivos de configuración de Maven y código fuente
COPY pom.xml .
COPY src ./src

# Construir el proyecto (salta tests para acelerar)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime - correr la aplicación con OpenJDK
FROM openjdk:23-jdk-slim
WORKDIR /app

# Copiar el JAR generado en la etapa anterior
COPY --from=build /app/target/ecoWater-0.0.1-SNAPSHOT.jar /app/ecoWater-0.0.1-SNAPSHOT.jar

# Copiar archivo de configuración firebase
COPY src/main/resources/firebase-service-account.json /app/resources/

EXPOSE 8080

CMD ["java", "-jar", "ecoWater-0.0.1-SNAPSHOT.jar"]