package com.example.angularschool.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnterAdminConstraint implements ConstraintValidator<NotAdmin,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !value.equalsIgnoreCase("admin");
    }
}
