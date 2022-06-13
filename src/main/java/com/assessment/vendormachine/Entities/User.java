package com.assessment.vendormachine.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private int deposit;
    private int totalSpent;
    private boolean isActive = false;

    @Enumerated(value = EnumType.STRING)
    private ROLE role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sellerld")
    @JsonIgnoreProperties({"seller"})
    private List<Product> soldProducts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "buy_transactions", joinColumns = {
            @JoinColumn(name = "buyer_id")}, inverseJoinColumns = {
            @JoinColumn(name = "bought_product_id")})
    @JsonIgnoreProperties({"buyers"})
    private List<Product> boughtProducts;


}
