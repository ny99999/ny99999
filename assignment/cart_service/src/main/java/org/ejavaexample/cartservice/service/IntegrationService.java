package org.ejavaexample.cartservice.service;

import org.ejavaexample.cartservice.controller.dto.ItemDTO;
import org.ejavaexample.cartservice.controller.dto.OrderDTO;
//import edu.bjtu.microservice.cart.domain.Cart;
import org.ejavaexample.cartservice.domain.Item;
//import edu.bjtu.microservice.cart.domain.ProductOverview;
import org.ejavaexample.cartservice.domain.UserInfo;
//import edu.bjtu.stream.model.Customer;
//import edu.bjtu.stream.producer.service.KafkaSender;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

//import lombok.AllArgsConstructor;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

/**
 * Provide remote and mock implementations to others integration services,
 * like billing, delivery and review services.
 */
@Service
//@AllArgsConstructor
public class IntegrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationService.class);

	@Autowired
	private KafkaTemplate<String, OrderDTO> kafkaTemplate;

	@Value("${spring.kafka.topics.payment}")
	private String topicName;

//  private final RestTemplate restTemplate;
//  private final WebClient.Builder webClientBuilder;
//  private final String USER_BASE_URL = "http://localhost:8082/api/user/";
//  private final String PRODUCT_BASE_URL = "http://localhost:8083/api/product/";
//  private final String USER_SERVICE_NAME = "USER-SERVICE";
//  private final String PRODUCT_SERVICE_NAME = "PRODUCT-SERVICE";
    @Autowired
	private UserFeignClient userFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;



    public UserInfo getRemoteUserInfo(Long userId) {

        // IMPLEMENTATION 01: using RestTemplate
        // var user = fetchDataWithRestTemplate("http://" + USER_SERVICE_NAME + "/api/user/", userId, UserInfo.class);

        // IMPLEMENTATION 02: using WebClient
        // var user = fetchDataWithWebClient("http://" + USER_SERVICE_NAME + "/api/user/", userId, UserInfo.class);

        // IMPLEMENTATION 03: using Feign
        var user = userFeignClient.findById(userId);

        return user;
    }

    public List<Item> getRemoteProductItemsInfo(List<Item> items) {
        items.forEach(item -> {

            // IMPLEMENTATION 01: using RestTemplate
            // var product = fetchDataWithRestTemplate("http://" + PRODUCT_SERVICE_NAME + "/api/product", item.getProduct().getId(), ProductOverview.class);

            // IMPLEMENTATION 02: using WebClient
            // var product = fetchDataWithWebClient("http://" + PRODUCT_SERVICE_NAME  + "/api/product", item.getProduct().getId(), ProductOverview.class);

            // IMPLEMENTATION 03: using Feign
            var product = productFeignClient.findById(item.getProduct().getId());

            item.setProduct(product);
        });

        return items;
    }
    
    public List<Item> getRemoteProductItemsInfo1(List<ItemDTO> items) {
    	List<Item> products = new ArrayList<Item>();
        items.forEach(item -> {

            // IMPLEMENTATION 01: using RestTemplate
            // var product = fetchDataWithRestTemplate("http://" + PRODUCT_SERVICE_NAME + "/api/product", item.getProduct().getId(), ProductOverview.class);

            // IMPLEMENTATION 02: using WebClient
            // var product = fetchDataWithWebClient("http://" + PRODUCT_SERVICE_NAME  + "/api/product", item.getProduct().getId(), ProductOverview.class);

            // IMPLEMENTATION 03: using Feign
            var product = productFeignClient.findById(item.getProductId());
            Item itm = new Item();
            itm.setProduct(product);
            itm.setQuantity(item.getQuantity());
            products.add(itm);
        });

        return products;
    }

    public void submitToBilling(OrderDTO order) {
        // Pretend to submit to Billing Service
		this.kafkaTemplate.send(topicName, order);
		LOGGER.info("Order (order id: " + order.getId() + ") was sent to Kafka Topic - " + topicName);

    }

    public void notifyToDelivery(OrderDTO order) {
        // Pretend to submit to Delivery Service
    }

    public void askForUserReview(OrderDTO order) {
        // Pretend to submit to Review Service
    }

     // Generic implementation for Rest Template call
//    private <T> T fetchDataWithRestTemplate(String url, Long id, Class<T> clazz) {
//        return restTemplate.getForObject(url + id, clazz);
//    }

    // Generic implementation for WebClient
//    private <T> T fetchDataWithWebClient(String url, Long id, Class<T> clazz) {
//        return webClientBuilder.build().get()
//                .uri(url + id).retrieve()
//                .bodyToMono(clazz)
//                .block(); // makes sync
//    }

//    @Deprecated   // replaced by generic method
//    private UserInfo fetchUserWithRestTemplate(Long userId) {
//        return restTemplate.getForObject("http://" + USER_SERVICE_NAME + "/api/user/" + userId, UserInfo.class);
//    }
//
//    @Deprecated   // replaced by generic method
//    private ProductOverview fetchProductWithRestTemplate(Long productId) {
//        return restTemplate.getForObject("http://" + PRODUCT_SERVICE_NAME + "/api/product/" + productId, ProductOverview.class);
//    }

	private List<OrderDTO> allList = new ArrayList<>(); 
	private List<OrderDTO> currentList = new ArrayList<>(); 

	@KafkaListener(topics = "${spring.kafka.topics.order}", groupId = "${spring.kafka.consumer.group-id}")
	public void recieveData(OrderDTO order) {
		LOGGER.info("Order:" + order.getId() + " recieved from kafka ordertopic.");
		//LOGGER.info("Data - " + student + " recieved");
		System.out.println("Received Message - order id : " + order.getId());
		System.out.println("Received Message - user name: " + order.getUserName());
        currentList.add(order);
 	}
	
	@KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.topics.order}", partitionOffsets = {
		    @PartitionOffset(partition = "0", initialOffset = "0") }), containerFactory = "kafkaListenerContainerFactory",
		    groupId = "group-2")
	public void addtoList(OrderDTO order) {
		LOGGER.info("Order:" + order.getId() + " recieved from kafka ordertopic.");
		//LOGGER.info("Data - " + student + " recieved");
		System.out.println("Received Message - order id : " + order.getId());
		System.out.println("Received Message - user name: " + order.getUserName());
        allList.add(order);
	}
	
	public List<OrderDTO> getCurrent() {
		return currentList;
	}
	
	public List<OrderDTO> getAll() {
		return allList;
	}
    
}
