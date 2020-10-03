FROM openjdk:11

COPY ./target/email-service-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch email-service-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","email-service-0.0.1-SNAPSHOT.jar"]