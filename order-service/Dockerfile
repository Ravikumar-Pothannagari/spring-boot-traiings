FROM java:8-alpine
ADD target/order-service-0.0.1-SNAPSHOT.jar order-service-0.0.1-SNAPSHOT.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","order-service-0.0.1-SNAPSHOT.jar"]