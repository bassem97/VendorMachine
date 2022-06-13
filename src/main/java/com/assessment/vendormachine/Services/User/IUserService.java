package com.assessment.vendormachine.Services.User;

import com.assessment.vendormachine.Entities.User;
import com.assessment.vendormachine.Utils.BuyResponse;
import com.assessment.vendormachine.Utils.ChangePasswordVM;

public interface IUserService {
    User findByUsername(String username);

    User deposit(int amount);

    User resetDeposit();

    BuyResponse buy(Long productId, int quantity);

    Boolean changePassword(ChangePasswordVM changePasswordVM);


}
