package org.ejavaexample.userservice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor
@Data
@Entity
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;
    
    private String mobile;

    private LocalDate birthdate;

    private String address;
        
    private String education;
    
    private String occupation;
    
    private String id_card_no;

}
