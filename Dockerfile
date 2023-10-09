FROM eclipse-temurin:17

LABEL mentainer="thaipeiidev@gmail.com"

WORKDIR /app

COPY /target/mobile-0.0.1-SNAPSHOT.jar /app/mobile-docker.jar

ENTRYPOINT ["java", "-jar", "mobile-docker.jar"]