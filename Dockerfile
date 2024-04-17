FROM openjdk:21
VOLUME /tmp
EXPOSE 8085
ADD ./target/ticket-0.0.1-SNAPSHOT.jar airplaneApp.jar
ENTRYPOINT ["java", "-jar", "airplaneApp.jar"]