package org.ejavaexample.cartservice.controller;

import org.ejavaexample.cartservice.controller.dto.CartDTO;
import org.ejavaexample.cartservice.controller.dto.OrderDTO;
import org.ejavaexample.cartservice.controller.dto.RequestDTO;
import org.ejavaexample.cartservice.controller.dto.ResponseDTO;
import org.ejavaexample.cartservice.service.IntegrationService;
import org.ejavaexample.cartservice.service.ShoppingCartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/order")
@AllArgsConstructor
public class OrderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
    private ShoppingCartService service;
    @Autowired
    private IntegrationService integrationService;

	//private CartMapper mapper;

    @PostMapping
    public ResponseEntity<ResponseDTO> submit(@RequestBody RequestDTO requestDTO) {
        //Cart cart = CartMapper.INSTANCE.toModel(requestDTO);
        LOGGER.info("An order is placed by post to /api/order");        
        var cart = service.purchase(requestDTO);
        //ResponseDTO responseDTO = CartMapper.INSTANCE.toResponseDTO(cart);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setInfo("Order (order id:" + cart.getId() + " ) has been placed, please check up the order state later.");
        responseDTO.setTime(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));

        return ResponseEntity.created(URI.create(cart.getId())).body(responseDTO);
    }
    
	@GetMapping("/all")
	public List<OrderDTO> getAll() {
        LOGGER.info("Querying order state in order topic");
		return integrationService.getAll();
				
	}


}
