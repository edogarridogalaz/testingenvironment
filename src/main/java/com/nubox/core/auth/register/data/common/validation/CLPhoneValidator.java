package com.nubox.core.auth.register.data.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CLPhoneValidator implements ConstraintValidator<CLPhone, String> {

    @Override
    public void initialize(CLPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validatePhone(value);
    }

    private boolean validatePhone(String phone) {
        if (phone == null || phone.isEmpty() || phone.isBlank()) {
            return true;
        } else {
            phone = phone.trim();
            if (phone.startsWith("+56")) {
                if (phone.length() == 12) {
                    return true;
                }
            }
        }
        return false;
    }
}
