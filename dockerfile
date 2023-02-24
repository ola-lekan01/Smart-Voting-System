FROM openjdk:17-jdk

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

COPY src ./src

RUN ./mvnw dependency:resolve

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]