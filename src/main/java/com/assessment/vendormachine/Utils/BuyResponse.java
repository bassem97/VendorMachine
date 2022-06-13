package com.assessment.vendormachine.Utils;

import com.assessment.vendormachine.Entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class BuyResponse implements Serializable {
    private int totalSpent;
    private int depositLeft;
    private List<Integer> coinsLeft;
    @JsonIgnoreProperties({"seller", "buyers", "amountAvailable"})
    private List<Product> boughtProducts;

    public BuyResponse(int totalSpent, int depositLeft, List<Product> boughtProducts) {
        this.totalSpent = totalSpent;
        this.depositLeft = depositLeft;
        this.boughtProducts = boughtProducts;
        this.coinsLeft = new ArrayList<>();

        // convert depositLeft into  coins and put in coinsLeft
        while (depositLeft > 0)
            if (depositLeft >= 100) {
                coinsLeft.add(100);
                depositLeft -= 100;
            } else if (depositLeft >= 50) {
                coinsLeft.add(50);
                depositLeft -= 50;
            } else if (depositLeft >= 20) {
                coinsLeft.add(20);
                depositLeft -= 20;
            } else if (depositLeft >= 10) {
                coinsLeft.add(10);
                depositLeft -= 10;
            } else if (depositLeft >= 5) {
                coinsLeft.add(5);
                depositLeft -= 5;
            }


    }

}
