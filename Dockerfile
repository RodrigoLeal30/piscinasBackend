# Etapa 1: Construcción del proyecto
FROM amazoncorretto:20-alpine-jdk AS build
WORKDIR /app
COPY pom.xml .
RUN ./usr/bin/java -version
RUN ./usr/bin/java -version
RUN ./usr/bin/java -version
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final
FROM amazoncorretto:20-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar piscinas.jar
ENTRYPOINT ["java", "-jar", "/app/piscinas.jar"]