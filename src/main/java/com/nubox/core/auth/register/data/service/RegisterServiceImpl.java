package com.nubox.core.auth.register.data.service;

import com.nubox.core.auth.register.data.model.data.factory.RegisterFactory;
import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.data.model.response.RegisterResponse;
import com.nubox.core.auth.register.domain.acl.adapter.FusionAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {


    @Autowired
    FusionAuthRepository fusionAuthRepository;

    @Autowired
    RegisterFactory registerFactory;


    /**
     * create new User
     *
     * @param userRequest the person to be create
     * @return created user
     */
    public RegisterResponse create(UserRequest userRequest) {

        return registerFactory.create(fusionAuthRepository.register(userRequest));
    }


}
