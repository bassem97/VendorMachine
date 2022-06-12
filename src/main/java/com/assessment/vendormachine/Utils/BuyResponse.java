package com.assessment.vendormachine.Utils;

import com.assessment.vendormachine.Entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class BuyResponse implements Serializable {
    private int totalSpent;

    @JsonIgnoreProperties({"seller", "buyers", "amountAvailable"})
    private List<Product> boughtProducts;

}
