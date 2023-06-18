package org.ejavaexample.cartservice.controller;

import org.ejavaexample.cartservice.controller.dto.CartDTO;
import org.ejavaexample.cartservice.controller.dto.RequestDTO;
import org.ejavaexample.cartservice.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/cart")
@AllArgsConstructor
public class ShoppingCartController {

	@Autowired
    private ShoppingCartService service;
    //private CartMapper mapper;

	   @PostMapping
	    public ResponseEntity<CartDTO> submit(@RequestBody RequestDTO requestDTO) {
	        //Cart cart = CartMapper.INSTANCE.toModel(requestDTO);
	        
	        var cart = service.getCartItems(requestDTO);
	        //ResponseDTO responseDTO = CartMapper.INSTANCE.toResponseDTO(cart);
	        CartDTO cartDTO = new CartDTO();
	        cartDTO.setId(cart.getId());
	        cartDTO.setUserId(cart.getUser().getId());
	        cartDTO.setUserName(cart.getUser().getName());
	        cartDTO.setTotalPrice(cart.getTotalPrice());
	        cartDTO.setItems(cart.getItems());
	        return ResponseEntity.created(URI.create(cartDTO.getId())).body(cartDTO);
    }

}
