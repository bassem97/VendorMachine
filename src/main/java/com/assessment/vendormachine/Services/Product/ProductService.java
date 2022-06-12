package com.assessment.vendormachine.Services.Product;

import com.assessment.vendormachine.Entities.Product;
import com.assessment.vendormachine.Repositories.ProductRepository;
import com.assessment.vendormachine.Services.ICrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService implements IProductService, ICrudService<Product, Long> {


    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product, Long id) {
        if (productRepository.findById(id).isPresent()) {
            Product product1 = productRepository.findById(id).get();
            product1.setProductName(product.getProductName());
            product1.setCost(product.getCost());
            product1.setAmountAvailable(product.getAmountAvailable());
            product1.setUser(product.getUser());
            return productRepository.save(product1);
        }
        return null;
    }


    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }


}
