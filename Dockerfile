FROM openjdk:17-oracle

COPY target/GPTLearn-App-1.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/backend.jar"]
