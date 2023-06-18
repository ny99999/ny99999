package org.ejavaexample.cartservice.controller.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import org.ejavaexample.cartservice.domain.Item;

@AllArgsConstructor  @NoArgsConstructor
@Getter  @Setter
public class CartDTO {

    private String id;
    private Long userId;
    private String userName;
    private List<Item> items;
    private BigDecimal totalPrice;

}
