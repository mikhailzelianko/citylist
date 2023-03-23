FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/demoproject-0.0.1-SNAPSHOT.jar cityapp.jar
ENTRYPOINT ["java","-jar","/cityapp.jar"]