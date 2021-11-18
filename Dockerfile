FROM openjdk:11

COPY ./target/netflix-app-1.0.0.war netflix.war

EXPOSE 8080

CMD ["java", "-jar", "netflix.war"]