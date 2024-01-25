FROM adoptopenjdk/openjdk17:alpine-aarch64

RUN apk add --no-cache curl

COPY . /app

WORKDIR /app

RUN mvn package  # assuming Maven build

EXPOSE 8080

CMD ["java", "-jar", "/app/target/truckApp-0.0.1-SNAPSHOT.jar"]

