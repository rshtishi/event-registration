# event-registration

event-registration is an application that allows people to register for different events.

## Bussiness Case 

We need to provide a solution that will give the user the possibility of registration to an event online without the need to come to our offices. For successful 
registration, we need to send the users the registration id along with the success message.

## Implementation Details

We are creating a multi maven module project that has two modules:

 - event-registration-app
 - email-service

The event-registration-app module is a web application that offers the visitor the possibility to register. The signed user on this application can create events.

The email-service module is an application that will send emails to users. We have created a template that will be sent to visitors that are registered in the 
event-registration-app module.

For the registration to be completed we need to send an email to the user. Both modules need to communicate with each other to complete the registration process. 
This communication can be:

 - Synchronous (We can use REST or SOAP to implement this communication)
 - Asynchronous (We can user JMS, RabbitMQ, or Kafka to implement this communication)

In synchronous communication, we will be invoking the endpoint declared from the email-service and our registration process will be not finished until the email was 
sent to the user. While in asynchronous communication, we will be sending a message that will be piled in the queue. Our email-service will be triggered to send an 
email every time a new message arrives. 

We have chosen to implement the communication between the modules asynchronously because modules are loosely coupled with each other. Even if email-service modules 
fail, the event-registration-app module will continue to register users. When the email-service module is restarted again, it will read the queue and send all the 
emails that couldn't send during the time it was down.

We implemented asynchronous communication with:

 - JMS(ActiveMQ)
 - RabbitMQ
 - Kafka
 
 
 
 ## Setup


