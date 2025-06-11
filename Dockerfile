# Usa la imagen oficial de Java 21
FROM eclipse-temurin:21-jdk-alpine

# Crea un directorio para la app
WORKDIR /app

# Copia el archivo JAR generado por Maven al contenedor
COPY target/*.jar app.jar

# Expone el puerto por donde tu app se comunicará
EXPOSE 8080

# Comando para ejecutar tu app
ENTRYPOINT ["java", "-jar", "app.jar"]
