package com.nubox.core.auth.register.data.model.data.factory;

import com.nubox.core.auth.register.data.model.data.business.Register;
import com.nubox.core.auth.register.data.model.response.RegisterResponse;
import com.nubox.core.auth.register.domain.bo.RegisterBO;
import org.springframework.stereotype.Component;

@Component("registerFactory")
public class RegisterFactory {


    public RegisterResponse create(RegisterBO registerBO) {
        Register register = new Register();
        register.setRegistrationId(registerBO.getRegistrationId());
        register.setUserId(registerBO.getUserId());

        return new RegisterResponse(register);

    }
}
