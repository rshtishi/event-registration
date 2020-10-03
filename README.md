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
 
 
 ### JMS(ActiveMQ)
 
 JMS is a Java standard that defines a common API for working with message brokers. With JMS, all compliant implementations can be worked with via a common interface in much the same way that JDBC has given relational database operations a common interface. We are going to use ActiveMQ as broker implementation.
 
 #### Implementing the Producer
 
 The event-registration-app module will be our producer. Every time a user registers in our application, the event-registration-app module will create a message and 
 send it to the broker. To implement the producer we start by adding the dependencies needed:
 
 ```
 		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-activemq</artifactId>
		</dependency>
 ```
 
 Next, we add the configuration information for ActiveMQ:
 
 ```
 spring.activemq.broker-url=tcp://localhost:61616
 activemq.queue=new.registration
 ```
 
 We proceed with creating the beans needed to send the message to the broker:
 
 ```
 @Configuration
public class ActiveMQConfiguration {
	
	@Value("${spring.activemq.broker-url}")
	private String activemqUrl;
	@Value("${activemq.queue}")
	private String activemqQueue;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(activemqUrl);
	}
	
	@Bean
	public Queue destination() {
		return new ActiveMQQueue(activemqQueue);
	}
	
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory());
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());
		return jmsTemplate;
	}
	

    @Bean 
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        typeIdMappings.put("subscriber", AttendeeDto.class);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }
}
 ```
 
 Finally,  we have the producer class that will send messages to the broker:
 
 ```
 @Component
public class RegistrationEventProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Value("${activemq.queue}")
	private String destination ;
	
    public void sendTo(AttendeeDto attendee) {
        jmsTemplate.convertAndSend(destination, attendee);
    }

}
 ```
 
  #### Implementing the Consumer
  
  The email-service module is consumer. This module is listening for the arrival of new message so it can send the mail. Below is the dependency needed to implement 
  consumer:
  
  ```
  		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-activemq</artifactId>
		</dependency>
  ```
  Next, we add the configuration information:
  
  ```
  #activemq configuration
spring.activemq.broker-url=tcp://localhost:61616
activemq.queue=new.registration
  ```
  
  Then, we created the bean that is needed for the consumer:
  
 ```
 @Configuration
public class ActiveMQConfiguration {

	@Value("${spring.activemq.broker-url}")
	private String activemqUrl;
	@Value("${activemq.queue}")
	private String activemqQueue;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(activemqUrl);
	}

	@Bean
	public Queue destination() {
		return new ActiveMQQueue(activemqQueue);
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory());
		jmsTemplate.setReceiveTimeout(1000);
		return jmsTemplate;
	}

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(jacksonJmsMessageConverter());
		return factory;
	}

    @Bean 
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_typeId");
        Map<String, Class<?>> typeIdMappings = new HashMap<String, Class<?>>();
        typeIdMappings.put("subscriber", EventSubscriber.class);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }

}
 ```
  
  Finally, we consumer class that listen to the arrival of new messages from the broker:
 
 ```
 @Component
public class EventNewSubscriberConsumer {
	
	@Autowired
	private EventNotificationService eventNotificationService;
	
	@JmsListener(destination = "new.registration")
	public void consumeMessage(EventSubscriber subscriber) {
		//eventNotificationService.notifyEventSubscriberByEmail(subscriber);
		System.out.println(subscriber);
	}

}
 ```
 
 
 ### RabbitMQ
 
 ### Kafka
 
 
 
 ## Setup

