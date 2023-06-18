package org.ejavaexample.cartservice.service;

import org.ejavaexample.cartservice.controller.dto.OrderDTO;
import org.ejavaexample.cartservice.controller.dto.RequestDTO;
import org.ejavaexample.cartservice.domain.Cart;
//import edu.bjtu.microservice.cart.domain.Item;
//import edu.bjtu.microservice.cart.domain.ProductOverview;
//import edu.bjtu.microservice.cart.domain.UserInfo;
//import lombok.AllArgsConstructor;
//import org.springframework.web.client.RestTemplate;
//import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
//@AllArgsConstructor
public class ShoppingCartService {
    @Autowired
    private IntegrationService integrationService;


    public Cart getCartItems1(Cart shoppingCart) {
        var uuid = UUID.randomUUID().toString();
        shoppingCart.setId(uuid);

        var user = integrationService.getRemoteUserInfo(shoppingCart.getUser().getId());
        shoppingCart.setUser(user);

        var items = integrationService.getRemoteProductItemsInfo(shoppingCart.getItems());
        shoppingCart.setItems(items);

//        integrationService.submitToBilling(shoppingCart);
//        integrationService.notifyToDelivery(shoppingCart);
//        integrationService.askForUserReview(shoppingCart);
 
        return shoppingCart;
    }

    public Cart getCartItems(RequestDTO requestDTO) {
        var uuid = UUID.randomUUID().toString();
        Cart shoppingCart = new Cart();
        shoppingCart.setId(uuid);

        var user = integrationService.getRemoteUserInfo(requestDTO.getUserId());
        shoppingCart.setUser(user);

        var items = integrationService.getRemoteProductItemsInfo1(requestDTO.getItems());
        shoppingCart.setItems(items);

//        integrationService.submitToBilling(shoppingCart);
//        integrationService.notifyToDelivery(shoppingCart);
//        integrationService.askForUserReview(shoppingCart);

        return shoppingCart;
    }
    
    @SuppressWarnings("null")
	public OrderDTO purchase(RequestDTO requestDTO) {
        String oid = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        OrderDTO order = new OrderDTO();
        order.setId(oid);
         
        var user = integrationService.getRemoteUserInfo(requestDTO.getUserId());
        order.setUserId(user.getId());
        order.setUserName(user.getName());

        var items = integrationService.getRemoteProductItemsInfo1(requestDTO.getItems());
        order.setItems(items);
        BigDecimal totalPrice = null;
		if (items != null)
        	totalPrice  = items.stream()
            .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    
        order.setTotalPrice(totalPrice);
        order.setPayState("uppaid");
        order.setDeliverState("undelivered");

        integrationService.submitToBilling(order);
        integrationService.notifyToDelivery(order);
        integrationService.askForUserReview(order);

        return order;
    }
   

}
