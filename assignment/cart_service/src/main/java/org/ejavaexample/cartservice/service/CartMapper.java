package org.ejavaexample.cartservice.service;

import org.ejavaexample.cartservice.controller.dto.CartDTO;
import org.ejavaexample.cartservice.controller.dto.ItemDTO;
import org.ejavaexample.cartservice.controller.dto.RequestDTO;
import org.ejavaexample.cartservice.controller.dto.ResponseDTO;
import org.ejavaexample.cartservice.domain.Cart;
import org.ejavaexample.cartservice.domain.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel="spring")
public interface CartMapper {

	CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
	
    @Mapping(source = "userId", target = "user.id")
    Cart toModel(CartDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    CartDTO toResponseDTO(Cart model);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "productName", target = "product.name")
    Item toModel(ItemDTO dto);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "productName", target = "product.name")
    List<Item> toModel(List<ItemDTO> dto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ItemDTO toDTO(Item model);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    List<ItemDTO> toDTO(List<Item> model);

}
