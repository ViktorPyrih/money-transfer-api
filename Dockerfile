FROM amazoncorretto:17
WORKDIR /app
COPY build/libs/money-transfer-api-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
