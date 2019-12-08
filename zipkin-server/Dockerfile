FROM openjdk:8
ADD ./target/zipkin-server-0.0.1-SNAPSHOT.jar zipkin-server-0.0.1-SNAPSHOT.jar
EXPOSE 9411
ENTRYPOINT ["java","-jar","zipkin-server-0.0.1-SNAPSHOT.jar"]