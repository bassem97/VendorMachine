package com.assessment.vendormachine;

import com.assessment.vendormachine.Services.User.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    // test deposit
    @Test
    public void deposit() {
        userService.deposit(100, 1L);
    }

    // test buy
    @Test
    public void buy() {
        userService.buy(3L, 1L, 2);
    }

}
