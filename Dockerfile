FROM amazoncorretto:20-alpine-jdk

# Copia el archivo JAR al contenedor
COPY target/backend-0.0.1-SNAPSHOT.jar piscinas.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/piscinas.jar"]