package com.assessment.vendormachine.Services.User;
import com.assessment.vendormachine.Entities.User;

public interface IUserService {
    User findByUsername(String username);
}