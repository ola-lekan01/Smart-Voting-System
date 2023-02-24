FROM maven:3.8.3-openjdk-17 AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

COPY src ./src

RUN ./mvnw dependency:resolve

FROM adoptopenjdk:17-jre-hotspot

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the builder stage to the runtime stage
COPY --from=builder /app/target/myapp.jar .

EXPOSE ${PORT}

CMD ["./mvnw", "spring-boot:run"]