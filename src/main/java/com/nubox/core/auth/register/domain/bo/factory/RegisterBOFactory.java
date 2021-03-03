package com.nubox.core.auth.register.domain.bo.factory;

import com.nubox.core.auth.register.domain.bo.RegisterBO;
import org.springframework.stereotype.Component;

@Component("registerBOFactory")
public class RegisterBOFactory {


    public RegisterBO create(Integer status, String userId, String registrationId) {
        return new RegisterBO(status, userId, registrationId);

    }
}
