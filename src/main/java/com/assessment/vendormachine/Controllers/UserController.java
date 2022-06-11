package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Services.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/register")
    public User add(@RequestBody User user) {
        log.info("Registering user: {}", user);
        return userService.add(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable("id") Long id) {
        return userService.update(user, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }


    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
}
