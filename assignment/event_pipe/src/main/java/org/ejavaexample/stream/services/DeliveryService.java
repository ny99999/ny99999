package org.ejavaexample.stream.services;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.ejavaexample.cartservice.controller.dto.OrderDTO;
import org.ejavaexample.cartservice.service.IntegrationService;

@Service
public class DeliveryService {
	
		private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryService.class);
		@Autowired
		private KafkaTemplate<String, OrderDTO> kafkaTemplate;

		@Value("${spring.kafka.topics.order}")
		private String ordertopic;

		@KafkaListener(topics = "${spring.kafka.topics.delivery}", groupId = "${spring.kafka.consumer.group-id}")
		public void process_delivery_events(OrderDTO order) {
			LOGGER.info("Order: " + order.getId() + " recieved from topic delivery");
			//LOGGER.info("Data - " + student + " recieved");
			System.out.println("Received Message - order id : " + order.getId());
			System.out.println("Received Message - user name: " + order.getUserName());
			
			order.setDeliverState("DELIVER");
			order.setDeliverDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
	        // Pretend to submit to Billing Service
			this.kafkaTemplate.send(ordertopic, order);
			LOGGER.info("Order: " + order.toString() + " sent to Kafka Topic - " + ordertopic);

	 	}

}
