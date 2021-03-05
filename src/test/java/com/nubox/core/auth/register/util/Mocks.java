package com.nubox.core.auth.register.util;

import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import com.nubox.core.auth.register.data.model.request.UserRequest;
import io.fusionauth.domain.User;
import io.fusionauth.domain.UserRegistration;
import io.fusionauth.domain.api.user.RegistrationResponse;

import java.util.UUID;

public final class Mocks {

    public static UserRequest getUserRequestOk() {
        UserRequest request = new UserRequest();
        request.setEmail("test@nubox.com");
        request.setFullName("name");
        request.setPhone("+56123456789");
        request.setPassword("******");
        return request;
    }

    public static UserRequest getUserRequestNoOk() {
        UserRequest request = new UserRequest();
        request.setEmail("testnubox.com");
        request.setFullName("name");
        request.setPhone(null);
        request.setPassword("******");
        return request;
    }

    public static ClientResponse<RegistrationResponse, Errors> getClientResponseOk(UUID registrationId, UUID userId) {
        ClientResponse<RegistrationResponse, Errors> response = new ClientResponse<>();
        response.status = 200;
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.id = registrationId;
        User user = new User();
        user.id = userId;
        response.successResponse = new RegistrationResponse(user, userRegistration);
        return response;
    }

    public static ClientResponse<RegistrationResponse, Errors> getClientResponseWithErrors(String errorCode) {
        ClientResponse<RegistrationResponse, Errors> response = new ClientResponse<>();
        response.status = 400;
        Errors errorResponse = new Errors();
        errorResponse.addFieldError("password", errorCode, "");
        response.errorResponse = errorResponse;
        return response;
    }
    public static ClientResponse<RegistrationResponse, Errors> getClientResponseWithErrorsEmpty() {
        ClientResponse<RegistrationResponse, Errors> response = new ClientResponse<>();
        response.status = 400;
        Errors errorResponse = new Errors();
        response.errorResponse = errorResponse;
        return response;
    }

}
