FROM maven:3.5-jdk-8 as maven

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY ./src/  ./src/

RUN mvn package

FROM openjdk:8u171-jre-alpine

WORKDIR /var/www/astra-project/companies-service

COPY --from=maven target/companies-service-*.jar ./

EXPOSE 8080

ENTRYPOINT ["java","-jar", "./companies-service-0.0.1-SNAPSHOT.jar"]
