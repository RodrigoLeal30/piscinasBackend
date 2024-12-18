FROM amazoncorretto:20-alpine-jdk

COPY target/backend-0.0.1-SNAPSHOT.jar piscinas.jar

ENTRYPOINT  ["java" , ".jar" , "/piscinas.jar"]