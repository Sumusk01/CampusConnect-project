FROM
maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WoRKDIR /app

COPY --from=build /app/target/*.jar app.jar

Expose 10000

ENTRYPOINT ["java", "-jar", "app.jar"]