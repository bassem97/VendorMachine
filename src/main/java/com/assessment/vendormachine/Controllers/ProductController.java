package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.Product;
import com.assessment.vendormachine.Services.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public List<Product> findAll() {
		return productService.findAll();
	}

 	@PostMapping("/")
	public Product add(@RequestBody Product product) {
		return productService.add(product);
	}

 	@PutMapping("/{id}")
	public Product update(@RequestBody Product product, @PathVariable("id") Long id) {
		return productService.update(product, id);
	}

 	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) {
		productService.delete(id);
	}


 	@GetMapping("/{id}")
	public Product findById(@PathVariable("id") Long id) {
		return productService.findById(id);
	}
}
