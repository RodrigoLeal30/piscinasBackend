# Etapa 1: Construcción del proyecto
FROM amazoncorretto:20-alpine-jdk AS build
WORKDIR /app

# Instala Maven
RUN apk add --no-cache maven

# Copia el archivo pom.xml y descarga dependencias para cachear
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el código fuente y compila el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final
FROM amazoncorretto:20-alpine-jdk
WORKDIR /app

# Copia el archivo JAR generado en la etapa de construcción
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar piscinas.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/piscinas.jar"]
