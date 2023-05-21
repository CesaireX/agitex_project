FROM openjdk:11
VOLUME /tmp
RUN mvn package -DskipTests
EXPOSE 8080
COPY target/agitex-0.0.1-SNAPSHOT.jar agitex.jar
ENTRYPOINT ["java","-jar","/agitex.jar"]
