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
 
  RabbitMQ is an implementation AMQP (which stands for 'Advanced Message Queueing Protocol'). AMQP is application layer protocol fordelivering messages across 
  multiple languages and platforms.
  
   #### Implementing the Producer
   
   First, add the following dependencies in the pom file:
   
   ```
   		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
   ```
   
   Next, add the configuration information in application properties file:
   
   ```
 #rabbitmq
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin

rabbitmq.routing-key=new.registration
   ```
   
   Then, create the bean needed for the producer:
   
   ```
@Configuration
public class RabbitMQConfiguration {

	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.username}")
	private String user;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.port}")
	private Integer port;
	@Value("${rabbitmq.routing-key}")
	private String routingKey ;

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setUsername(user);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setConnectionFactory(connectionFactory());
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		rabbitTemplate.setRoutingKey(routingKey);
		return rabbitTemplate;
	}
}
   ```
   
   Finally, implement the producer as below:
   
   ```
@Component
public class RegistrationEventProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Value("${rabbitmq.routing-key}")
	private String destination ;
	
    public void sendTo(AttendeeDto attendee) {
        rabbitTemplate.convertAndSend(destination, attendee);
    }

}
   ```
   
   #### Implementing the Consumer
   
   First, the following dependencies in the pom file:
   
   ```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
   ```
   
   Next, add the configuration information in the application properties file:
   
   ```
#rabbitmq
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=admin

rabbitmq.queue=new.registration
   ```
   
   Then, create the bean needed for the consumer:
   
   ```
@Configuration
public class RabbitMQConfiguration {

	@Value("${spring.rabbitmq.host}")
	private String host;
	@Value("${spring.rabbitmq.username}")
	private String user;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.port}")
	private Integer port;
	@Value("${rabbitmq.queue}")
	private String queue;

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
		connectionFactory.setUsername(user);
		connectionFactory.setPassword(password);
		connectionFactory.setPort(port);
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setConnectionFactory(connectionFactory());
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
   ```
   
   Finally, implement the consumer as below:
   
   ```
@Component
public class EventNewSubscriberConsumer {
	
	@Autowired
	private EventNotificationService eventNotificationService;
	
	@RabbitListener(queues = "new.registration")
	public void consumeMessage(EventSubscriber subscriber) throws Exception {
		eventNotificationService.notifyEventSubscriberByEmail(subscriber);
		System.out.println(subscriber);
	}

}
   ```
 
 ### Kafka
 
 
   #### Implementing the Producer
   
   First, add the following dependencies in the pom file:
   
   ```
   		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka</artifactId>
		</dependency>
   ```
   
   Next, add the configuration information in application properties file:
   
   ```
   #kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.template.default-topic=new-registration
   ```
   
   Then, create the bean needed for the producer:
   
   ```
   @Configuration
public class KafkaConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public ProducerFactory<String, AttendeeDto> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AttendeeDtoSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, AttendeeDto> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
}
   ```
   
   Finally, implement the producer as below:
   
   ```
@Component
public class RegistrationEventProducer {

	@Autowired
	private KafkaTemplate<String, AttendeeDto> kafkaTemplate;

	@Value("${spring.kafka.template.default-topic}")
	private String destination;

	public void sendTo(AttendeeDto attendee) {
		kafkaTemplate.send(destination, attendee);
	}

}
   ```
   
   
   #### Implementing the Consumer
   
   First, the following dependencies in the pom file:
   
   ```
   		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka</artifactId>
		</dependency>
   ```
   
   Next, add the configuration information in the application properties file:
   
   ```
   #kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.template.default-topic=new-registration
   ```
   
   Then, create the bean needed for the consumer:
   
   ```
 @Configuration
public class KafkaConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	private static final String GROUP_ID = "email";

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return props;
	}

	@Bean
	public ConsumerFactory<String, EventSubscriber> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
				new JsonDeserializer<>(EventSubscriber.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, EventSubscriber> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, EventSubscriber> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}
   ```
   
   Finally, implement the consumer as below:
   
   ```
   @Component
public class EventNewSubscriberConsumer {

	@Autowired
	private EventNotificationService eventNotificationService;

	@KafkaListener(topics = "new-registration", groupId = "email")
	public void consumeMessage(EventSubscriber subscriber) throws Exception {
		eventNotificationService.notifyEventSubscriberByEmail(subscriber);
		System.out.println(subscriber);
	}

}
   ```
 
 
 ## Setup
 
 - Version 1 (ActiveMQ)
 	- install ActiveMQ
	- start ActiveMQ
	- create a queue named ```new.registration```
	- run ```mvn clean install``` on event-registration project
	- start event-registration-app application with maven command: ```mvn spring-boot:run```
	- start email-service application with maven command ```mvn spring-boot:run```
	- access the application in url: ```http://localhost:8081```
	
 - Version 2 (RabbitMQ)
 	- install RabbitMQ
	- start RabbitMQ
	- create queue ```new.registration```
	- create exchange ```new.registration```
	- create binding of exchange with queue created above with ```new.registration``` as routing key
	- run ```mvn clean install``` on event-registration project
	- start event-registration-app application with maven command: ```mvn spring-boot:run```
	- start email-service application with maven command ```mvn spring-boot:run```
	- access the application in url: ```http://localhost:8081```
 
 
  - Version 3 (Kafka)
 	- install Kafka
	- start Kafka
	- create topic ```new.registration```
	- run ```mvn clean install``` on event-registration project
	- start event-registration-app application with maven command: ```mvn spring-boot:run```
	- start email-service application with maven command ```mvn spring-boot:run```
	- access the application in url: ```http://localhost:8081```

### Setup with Docker

Prerequisite:

	- Docker should be already installed
	- Docker-compose should be installed
	
Setup step:

	- checkout version you want to run
		- ```git checkout version1.0``` (Event Registration with ActiveMQ )
		- ```git checkout version2.0``` (Event Registration with RabbitMQ)
		- ```git checkout version3.0``` (Event Registration with Kafka)
	- open terminal from event_registration folder
	- run the command : ```docker-compose up```
	

