package com.nubox.core.auth.register.data.service;

import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.data.model.response.RegisterResponse;


public interface RegisterService {


    RegisterResponse create(UserRequest userRequest);

}
