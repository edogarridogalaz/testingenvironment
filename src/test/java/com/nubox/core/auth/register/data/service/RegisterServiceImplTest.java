package com.nubox.core.auth.register.data.service;

import com.nubox.core.auth.register.data.model.data.factory.RegisterFactory;
import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.data.model.response.RegisterResponse;
import com.nubox.core.auth.register.domain.acl.adapter.FusionAuthRepository;
import com.nubox.core.auth.register.domain.bo.RegisterBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

    @Mock
    FusionAuthRepository fusionAuthRepository;

    @Mock
    RegisterFactory registerFactory;

    @InjectMocks
    private RegisterServiceImpl registerService;

    @Test
    void createRegister_OK() {

        //Given
        UserRequest request = new UserRequest();
        request.setEmail("test@nubox.com");
        request.setFullName("name");
        request.setPhone(null);
        request.setPassword("******");

        final String USER_ID = "user-id";
        final String REGISTRATION_ID = "registration-id";
        RegisterBO registerBO = new RegisterBO(200, USER_ID, REGISTRATION_ID);

        given(fusionAuthRepository.register(any(UserRequest.class))).willReturn(registerBO);
        given(registerFactory.create(any(RegisterBO.class))).willReturn(new RegisterFactory().create(registerBO));
        
        //When
        RegisterResponse result = registerService.create(request);

        //Then
        verify(fusionAuthRepository).register(any(UserRequest.class));
        then(result).isNotNull();
        then(result.getRegister()).isNotNull();
        then(result.getRegister().getUserId()).isEqualTo(USER_ID);
        then(result.getRegister().getRegistrationId()).isEqualTo(REGISTRATION_ID);
    }

}