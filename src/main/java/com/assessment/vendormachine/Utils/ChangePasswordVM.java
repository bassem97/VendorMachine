package com.assessment.vendormachine.Utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangePasswordVM {
    private String newPassword;
    private String oldPassword;
}
