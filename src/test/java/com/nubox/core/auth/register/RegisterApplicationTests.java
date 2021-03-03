package com.nubox.core.auth.register;

import com.nubox.core.auth.register.data.model.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@SpringBootTest
class RegisterApplicationTests {


    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testValidationHappyRegister() {

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@nubox.com");
        userRequest.setPassword("Nubox1234");
        userRequest.setFullName("Víctor");

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assertions.assertTrue(violations.isEmpty());

    }

    @Test
    public void testValidationNotHappyRegister() {

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("testnubox.com");
        userRequest.setPassword("1234");
        userRequest.setFullName("Víctor");

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        Assertions.assertFalse(violations.isEmpty());

        //Assertions.assertEquals(violations.iterator().next().getInvalidValue(), "");

    }

}
