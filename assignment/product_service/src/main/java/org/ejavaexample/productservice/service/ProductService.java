package org.ejavaexample.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ejavaexample.productservice.entity.Product;
import org.ejavaexample.productservice.repository.ProductRepository;

@Service
public class ProductService {
	
    @Autowired
    ProductRepository productRepository;
	
	   /* PRODUCT */
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public Optional<Product> getProduct(long id){
        return productRepository.findById(id);
    }
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }


}
