package com.assessment.vendormachine.Services.User;


import com.assessment.vendormachine.Entities.ROLE;
import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Repositories.UserRepository;
import com.assessment.vendormachine.Services.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService, ICrudService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User add(User user) {
        if (userRepository.findAll().isEmpty())
            user.setRole(ROLE.BUYER);
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user, Long aLong) {
        if (userRepository.findById(aLong).isPresent()) {
            User user1 = userRepository.findById(aLong).get();
            user1.setDeposit(user.getDeposit());
            user1.setPassword(bcryptEncoder.encode(user.getPassword()));
            user1.setRole(user.getRole());

            return userRepository.save(user1);
        }
        return null;
    }

    @Override
    public void delete(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User findById(Long aLong) {
        return userRepository.findById(aLong).get();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}


