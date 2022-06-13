package com.assessment.vendormachine.Controllers;


import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Security.SecurityConfig;
import com.assessment.vendormachine.Security.TokenProvider;
import com.assessment.vendormachine.Services.User.UserService;
import com.assessment.vendormachine.Services.User.UserServiceDetails;
import com.assessment.vendormachine.Utils.ChangePasswordVM;
import com.assessment.vendormachine.Utils.JwtRespone;
import com.assessment.vendormachine.Utils.LoginModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Import(SecurityConfig.class)
@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/auth/")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserServiceDetails userDetailsService;

    @Autowired
    private UserService userService;


    @PostMapping("login")
    public ResponseEntity<?> authenticate(@RequestBody LoginModel loginModel) {
//        log.error("username: " + userService.getUsersFromSessionRegistry().toString());
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User byUsername = userService.findByUsername(loginModel.getUsername());
        if (byUsername.isActive())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already logged in");

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginModel.getUsername(),
                        loginModel.getPassword()
                )
        );

//                if (principal instanceof UserDetails && ((UserDetails) principal).getUsername().equals(loginModel.getUsername()))
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already an active session using your account");
//
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginModel.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);


        final String token;
        token = tokenProvider.generateToken(userDetails, 1);
        byUsername.setActive(true);
        userService.update(byUsername, byUsername.getId());
        return ResponseEntity.ok(new JwtRespone(token));
    }


    @PostMapping("changePassword")
    public Boolean changePassword(@RequestBody ChangePasswordVM changePasswordVM) {
        return userService.changePassword(changePasswordVM);
    }

    @PostMapping("logout")
    public User logout() {
        userService.getCurrentUser().setActive(false);
        return userService.update(userService.getCurrentUser(), userService.getCurrentUser().getId());
    }


}
