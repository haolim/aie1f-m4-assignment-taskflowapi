# Stage 1: Build JAR with Maven

# pull maven with Java JDK 21 from dockerb hub
FROM maven:3.9-eclipse-temurin-21 AS build
# create a working directory if not exist. set it as working directory
WORKDIR /app
# copy maven configuration from project directory into /app (working directory)
COPY pom.xml .
# download dependencies
RUN mvn dependency:go-offline
# copy source code from src directory into /app/src (working directory)
COPY src ./src
# build the application and skip running tests during the build
# files matching .dockerignore patterns will not be included in the build
# clean removes the previous build; package compiles, test (execution is skipped), and package the app
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR

# pull Java 21 JRE image from docker hub
FROM eclipse-temurin:21-jre-alpine
# create a working directory if not exist. set it as working directory
WORKDIR /app
# copy the jar file from 'build' step, rename it to app.jar (assumes only one JAR file)
COPY --from=build /app/target/*.jar app.jar
# document the intended container port (optional as the actual port is defined by spring boot config)
EXPOSE 8080
# start the application
CMD ["java", "-jar", "app.jar"]