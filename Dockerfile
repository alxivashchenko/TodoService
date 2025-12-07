# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Copy the rest of the project
COPY src ./src

# Build application
RUN mvn -q -B package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
#FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy fat JAR from build stage (update JAR name if needed)
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
