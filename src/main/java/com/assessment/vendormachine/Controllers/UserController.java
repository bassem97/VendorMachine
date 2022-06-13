package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Services.User.UserService;
import com.assessment.vendormachine.Utils.BuyResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("")
    public ResponseEntity findAll() {
        if (userService.findAll().isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity add(@RequestBody User user) {
        if (userService.add(user) == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody User user, @PathVariable("id") Long id) {
        if (userService.update(user, id) == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find user");
        return ResponseEntity.ok(userService.update(user, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        if (userService.findById(id) == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find user");
        return ResponseEntity.ok(userService.findById(id));
    }


    @PostMapping("/deposit/{id}/{amount}")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity deposit(@PathVariable("amount") int amount, @PathVariable("id") Long id) {
        if (Arrays.asList(5, 10, 20, 50, 100).contains(amount)) {
            User deposit = userService.deposit(amount, id);
            if (deposit == null)
                return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find user");
            return ResponseEntity.ok(deposit);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("you can only deposit coins of 5, 10, 20, 50, 100");
    }

    // reset deposit
    @PostMapping("/resetDeposit")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity resetDeposit() {
        return ResponseEntity.ok(userService.resetDeposit());
    }

    // buy product
    @PostMapping("/buy/{productId}/{buyerId}/{quantity}")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public ResponseEntity buy(@PathVariable("productId") Long productId, @PathVariable("buyerId") Long buyerId, @PathVariable("quantity") int quantity) {
        BuyResponse buyResponse = userService.buy(productId, buyerId, quantity);
        if (buyResponse == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("you either don't have enough money or product quantity is not enough");
        return ResponseEntity.ok(buyResponse);

    }


}
