package com.assessment.vendormachine.Services.User;


import com.assessment.vendormachine.Entities.Product;
import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Repositories.UserRepository;
import com.assessment.vendormachine.Services.ICrudService;
import com.assessment.vendormachine.Services.Product.ProductService;
import com.assessment.vendormachine.Utils.BuyResponse;
import com.assessment.vendormachine.Utils.ChangePasswordVM;
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
    private ProductService productService;

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
            user1.setUsername(user.getUsername());

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
        User currentUser = getCurrentUser();
        currentUser.setDeposit(currentUser.getDeposit() + amount);
        return userRepository.save(currentUser);
    }


    public User resetDeposit() {
        final User currentUser = getCurrentUser();
        currentUser.setDeposit(0);
        return userRepository.save(currentUser);
    }

    @Override
    public BuyResponse buy(Long productId, int quantity) {
        final User currentUser = getCurrentUser();
        Product product = productService.findById(productId);
        int totalAmount = quantity * product.getCost();
        if (currentUser.getDeposit() >= totalAmount && product.getAmountAvailable() >= quantity) {
            currentUser.setDeposit(currentUser.getDeposit() - totalAmount);
            currentUser.setTotalSpent(currentUser.getTotalSpent() + totalAmount);
            productService.decreaseAmountAvailable(product, quantity);
            if (!currentUser.getBoughtProducts().contains(product))
                currentUser.getBoughtProducts().add(product);
            userRepository.save(currentUser);
            return new BuyResponse(currentUser.getTotalSpent(), currentUser.getDeposit(), currentUser.getBoughtProducts());

        }
        return null;
    }

    @Override
    public Boolean changePassword(ChangePasswordVM changePasswordVM) {
        final User currentUser = getCurrentUser();
        if (bcryptEncoder.matches(changePasswordVM.getOldPassword(), currentUser.getPassword())) {
            currentUser.setPassword(bcryptEncoder.encode(changePasswordVM.getNewPassword()));
            userRepository.save(currentUser);
            return true;
        } else return false;
    }


    public User getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User currentUser = findByUsername(authentication.getName());
        return currentUser;
    }


}


