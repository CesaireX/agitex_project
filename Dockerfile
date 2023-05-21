FROM adoptopenjdk:11-jre-hotspot

COPY target/agitex-0.0.1-SNAPSHOT.jar /app/agitex-app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "agitex-app.jar"]
