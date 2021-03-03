package com.nubox.core.auth.register.domain.bo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterBO {

    Integer status;
    String userId;
    String registrationId;


    public RegisterBO(Integer status,String userId, String registrationId) {
        this.status = status;
        this.userId = userId;
        this.registrationId = registrationId;
    }
}
