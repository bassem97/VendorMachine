package com.assessment.vendormachine.Services.Product;


import com.assessment.vendormachine.Entities.Product;

public interface IProductService {
    Product decreaseAmountAvailable(Product product, int quantity);

}
