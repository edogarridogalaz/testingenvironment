package com.nubox.core.auth.register.domain.acl.adapter;

import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.domain.bo.RegisterBO;

public interface FusionAuthRepository {

    
    RegisterBO register(UserRequest user);
}
