package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Services.User.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity findAll() {
        if (userService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity add(@RequestBody User user) {
        if (userService.add(user) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody User user, @PathVariable("id") Long id) {
        if (userService.update(user, id) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find user");
        }
        return ResponseEntity.ok(userService.update(user, id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userService.delete(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {
        if (userService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find user");
        }
        return ResponseEntity.ok(userService.findById(id));
    }
}
