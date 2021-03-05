package com.nubox.core.auth.register.domain.acl.adapter;

import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.domain.bo.RegisterBO;
import com.nubox.core.auth.register.domain.bo.translate.RegisterTranslate;
import com.nubox.core.auth.register.util.Mocks;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.api.user.RegistrationRequest;
import io.fusionauth.domain.api.user.RegistrationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FusionAuthAdapterTest {

    final UUID USER_REGISTRATION_ID = UUID.randomUUID();
    final UUID USER_ID = UUID.randomUUID();

    @Mock
    private FusionAuthClient fusionAuthClient;

    @Mock
    private RegisterTranslate registerTranslate;

    @InjectMocks
    private FusionAuthAdapter fusionAuthAdapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void register() {

        //Given
        ReflectionTestUtils.setField(fusionAuthAdapter, "aplicationId", "a3c0bec1-6ae1-4b22-81c0-8160cd31968e");

        UserRequest request = Mocks.getUserRequestOk();


        ClientResponse<RegistrationResponse, Errors> responseMock = Mocks.getClientResponseOk(USER_REGISTRATION_ID, USER_ID);
        RegisterBO registerBOMock = new RegisterBO(200, USER_ID.toString(), USER_REGISTRATION_ID.toString());

        given(fusionAuthClient.register(any(), any(RegistrationRequest.class))).willReturn(responseMock);
        given(registerTranslate.translate(any(ClientResponse.class))).willReturn(registerBOMock);

        //When
        RegisterBO result = fusionAuthAdapter.register(request);

        //Then
        then(result).isNotNull();
        then(result.getRegistrationId()).isEqualTo(USER_REGISTRATION_ID.toString());
        then(result.getUserId()).isEqualTo(USER_ID.toString());
    }
    @Test
    void register_noPhone() {

        //Given
        ReflectionTestUtils.setField(fusionAuthAdapter, "aplicationId", "a3c0bec1-6ae1-4b22-81c0-8160cd31968e");

        UserRequest request = Mocks.getUserRequestNoOk();

        ClientResponse<RegistrationResponse, Errors> responseMock = Mocks.getClientResponseOk(USER_REGISTRATION_ID, USER_ID);
        RegisterBO registerBOMock = new RegisterBO(200, USER_ID.toString(), USER_REGISTRATION_ID.toString());

        given(fusionAuthClient.register(any(), any(RegistrationRequest.class))).willReturn(responseMock);
        given(registerTranslate.translate(any(ClientResponse.class))).willReturn(registerBOMock);

        //When
        RegisterBO result = fusionAuthAdapter.register(request);

        //Then
        then(result).isNotNull();
        then(result.getRegistrationId()).isEqualTo(USER_REGISTRATION_ID.toString());
        then(result.getUserId()).isEqualTo(USER_ID.toString());
    }
}