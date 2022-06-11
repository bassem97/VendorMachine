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

    @Enumerated(value = EnumType.STRING)
    private ROLE role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sellerld")
    @JsonIgnoreProperties({"user"})
    private List<Product> Product;


}
