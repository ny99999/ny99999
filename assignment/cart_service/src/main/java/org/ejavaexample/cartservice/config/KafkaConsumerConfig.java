package org.ejavaexample.cartservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.ejavaexample.cartservice.controller.dto.OrderDTO;

//import edu.bjtu.stream.model.Customer;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String kafkaServer;

	@Value("${spring.kafka.consumer.group-id}")
	private String kafkaGroupId;

	@Bean
	public ConsumerFactory<String, OrderDTO> consumerConfig() {
		// TODO Auto-generated method stub
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		//config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		//config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(OrderDTO.class));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderDTO>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, OrderDTO> listener = new ConcurrentKafkaListenerContainerFactory<>();
		listener.setConsumerFactory(consumerConfig());
		return listener;
	}

}
