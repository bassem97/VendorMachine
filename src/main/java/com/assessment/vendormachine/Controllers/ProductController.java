package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.Product;
import com.assessment.vendormachine.Services.Product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
@Slf4j
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
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity add(@RequestBody Product product) {
        return ResponseEntity.ok(productService.add(product));
    }

    @PutMapping("/{id}")
    // user can only update his own product and his role is seller
    @PreAuthorize("authentication.principal.username == @productService.findById(#id).getSeller().getUsername() && hasRole('ROLE_SELLER')")
    public ResponseEntity update(@RequestBody Product product, @PathVariable("id") Long id) {
        Product update = productService.update(product, id);
        if (update == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find product");
        }
        return ResponseEntity.ok(update);
    }


    @DeleteMapping("/{id}")
    // user can only delete his own product and his role is seller
    @PreAuthorize("authentication.principal.username == @productService.findById(#id).getSeller().getUsername() && hasRole('ROLE_SELLER')")
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
