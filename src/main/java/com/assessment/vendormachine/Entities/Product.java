package com.assessment.vendormachine.Entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity @Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int amountAvailable;
    private int cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sellerld")
    @JsonIgnoreProperties({"products"})
    private User user;

}
