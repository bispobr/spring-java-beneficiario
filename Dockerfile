FROM openjdk:21-ea-1-jdk-slim

WORKDIR /app

COPY target/beneficiario-0.0.1-SNAPSHOT.jar /app/beneficiario.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/beneficiario.jar"]