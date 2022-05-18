FROM openjdk:17-oracle
VOLUME /tmp
EXPOSE 8080
ADD target/final-project-aozcann-0.0.1-SNAPSHOT.jar final-project-aozcann.jar
ENTRYPOINT ["java", "-jar", "/final-project-aozcann.jar"]

#final-project-aozcann-0.0.1-SNAPSHOT.jar