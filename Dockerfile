FROM amazoncorretto:17

WORKDIR /app

COPY target/truckApp-0.0.1-SNAPSHOT.jar /app/truckApp-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "truckApp-0.0.1-SNAPSHOT.jar"]
