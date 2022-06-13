package com.assessment.vendormachine;

import com.assessment.vendormachine.Entities.Product;
import com.assessment.vendormachine.Services.Product.ProductService;
import com.assessment.vendormachine.Services.User.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    // test add product
    @Test
    public void get() {
        Product product = new Product();
        product.setProductName("test");
        product.setCost(10);
        product.setAmountAvailable(10);
        product.setSeller(userService.findById(1L));
        productService.add(product);
        productService.delete(product.getId());
    }

}
