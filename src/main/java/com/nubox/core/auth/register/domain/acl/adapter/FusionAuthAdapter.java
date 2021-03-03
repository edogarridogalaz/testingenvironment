package com.nubox.core.auth.register.domain.acl.adapter;

import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.domain.bo.RegisterBO;
import com.nubox.core.auth.register.domain.bo.translate.RegisterTranslate;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.User;
import io.fusionauth.domain.UserRegistration;
import io.fusionauth.domain.api.user.RegistrationRequest;
import io.fusionauth.domain.api.user.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.UUID;

@Repository("fusionAuthRepository")
public class FusionAuthAdapter implements FusionAuthRepository {

    @Value("${fusionauth.aplicationId}")
    String aplicationId;

    @Autowired
    FusionAuthClient getFusionAuthClient;

    @Autowired
    RegisterTranslate registerTranslate;


    @Override
    public RegisterBO register(UserRequest userRequest) {

        // Cree una instancia del usuario y proporcione los detalles del usuario
        User user = new User().with(u -> u.email = userRequest.getEmail())
                .with(u -> u.username = userRequest.getEmail())
                .with(u -> u.preferredLanguages.add(new Locale("es", "CL")))//TODO: revisar mas adelante
                .with(u -> u.password = userRequest.getPassword())
                .with(u -> u.fullName = userRequest.getFullName());
        if (userRequest.getPhone() != null)
            user.with(u -> u.mobilePhone = userRequest.getPhone().trim());

        // Se inicia el registro de usuario y  objecto request
        UserRegistration registration = new UserRegistration();
        registration.applicationId = UUID.fromString(aplicationId);
        registration.verified = false;

        RegistrationRequest request = new RegistrationRequest(user, registration);

        // Uso el objeto ClientResponse para capturar respuesta
        ClientResponse<RegistrationResponse, Errors> response = getFusionAuthClient.register(null, request);

        // Traducto respuesta segun exito o error
        return registerTranslate.translate(response);
    }


}
