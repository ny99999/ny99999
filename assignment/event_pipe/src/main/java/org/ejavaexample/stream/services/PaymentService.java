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
public class PaymentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
	@Autowired
	private KafkaTemplate<String, OrderDTO> kafkaTemplate;

	@Value("${spring.kafka.topics.delivery}")
	private String deliverytopic;

	@KafkaListener(topics = "${spring.kafka.topics.payment}", groupId = "${spring.kafka.consumer.group-id}")
	public void process_payment_events(OrderDTO order) {
		LOGGER.info("Order:" + order.getId() + " recieved from topic payment.");
		//LOGGER.info("Data - " + student + " recieved");
		System.out.println("Received Message - order id : " + order.getId());
		System.out.println("Received Message - user name: " + order.getUserName());
		
		order.setPayState("PAID");
		order.setPayDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        // Pretend to submit to Billing Service
		this.kafkaTemplate.send(deliverytopic, order);
		LOGGER.info("Order: " + order.toString() + " sent to Kafka Topic " + deliverytopic);

 	}

}
