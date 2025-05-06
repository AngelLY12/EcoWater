FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/ecoWater-0.0.1-SNAPSHOT.jar /app/ecoWater-0.0.1-SNAPSHOT.jar
COPY src/main/resources/firebase-service-account.json /app/resources/
EXPOSE 8080
CMD ["java", "-jar", "ecoWater-0.0.1-SNAPSHOT.jar"]
