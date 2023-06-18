package org.ejavaexample.productservice.entity;

import lombok.*;

//import javax.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@NoArgsConstructor 
@AllArgsConstructor
@Data
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String brand;

    private BigDecimal price;
    
    private Integer quantity;  //Quantity Available

}
