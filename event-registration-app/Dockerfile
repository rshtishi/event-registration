FROM openjdk:11

COPY ./target/event-registration-app-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch event-registration-app-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","event-registration-app-0.0.1-SNAPSHOT.jar"]