package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.Product;
import com.assessment.vendormachine.Services.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity findAll() {
        if (productService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("")
    public ResponseEntity add(@RequestBody Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Product product, @PathVariable("id") Long id) {
        if (productService.update(product, id) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find product");
        }
        return ResponseEntity.ok(productService.update(product, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        productService.delete(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        if (productService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find product");
        }
        return ResponseEntity.ok(productService.findById(id));
    }
}
