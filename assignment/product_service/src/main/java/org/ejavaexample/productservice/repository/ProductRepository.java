package org.ejavaexample.productservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;
import org.ejavaexample.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
