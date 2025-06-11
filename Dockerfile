# Etapa 1: construir el jar con Maven
FROM maven:3.8.7-eclipse-temurin-17 AS build

WORKDIR /app

# Copia pom y código fuente
COPY pom.xml .
COPY src ./src

# Construye el proyecto y genera el jar (sin tests para acelerar)
RUN mvn clean package -DskipTests

# Etapa 2: imagen final con JRE
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /

# Copia el jar desde la etapa build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Ejecuta la app
ENTRYPOINT ["java", "-jar", "app.jar"]
