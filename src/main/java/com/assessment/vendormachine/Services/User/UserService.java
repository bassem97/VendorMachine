package com.assessment.vendormachine.Services.User;


import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Repositories.UserRepository;
import com.assessment.vendormachine.Services.ICrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService implements IUserService, ICrudService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public User add(User user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(bcryptEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;

    }

    @Override
    public User update(User user, Long aLong) {
        if (userRepository.findById(aLong).isPresent()) {
            User user1 = userRepository.findById(aLong).get();
            user1.setDeposit(user.getDeposit());
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

    @Override
    public User deposit(int amount) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = findByUsername(authentication.getName());
        currentUser.setDeposit(currentUser.getDeposit() + amount);
        return userRepository.save(currentUser);
    }

}


