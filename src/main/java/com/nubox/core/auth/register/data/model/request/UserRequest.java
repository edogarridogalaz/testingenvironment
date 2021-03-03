package com.nubox.core.auth.register.data.model.request;

import com.nubox.core.auth.register.data.common.validation.CLPhone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRequest {

    @NotEmpty
    private String fullName;

    @NotEmpty
    @Email
    private String email;

    @CLPhone
    private String phone;

    @NotEmpty
    private String password;
}
