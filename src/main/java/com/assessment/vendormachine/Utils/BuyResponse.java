package com.assessment.vendormachine.Utils;

import com.assessment.vendormachine.Entities.Product;
import lombok.Data;

import java.util.List;

@Data
public class BuyResponse {
    private int totalSpent;
    private List<Product> products;

}
