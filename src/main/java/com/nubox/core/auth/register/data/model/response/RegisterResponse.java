package com.nubox.core.auth.register.data.model.response;

import com.nubox.core.auth.register.data.model.data.business.Register;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

    Register register;

    public RegisterResponse(Register register) {
        this.register = register;
    }
}
