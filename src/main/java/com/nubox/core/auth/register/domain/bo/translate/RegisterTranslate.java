package com.nubox.core.auth.register.domain.bo.translate;

import com.inversoft.error.Error;
import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import com.nubox.core.auth.register.data.common.exception.EmailException;
import com.nubox.core.auth.register.data.common.exception.PasswordException;
import com.nubox.core.auth.register.data.common.exception.RegisterException;
import com.nubox.core.auth.register.domain.bo.RegisterBO;
import com.nubox.core.auth.register.domain.bo.factory.RegisterBOFactory;
import io.fusionauth.domain.api.user.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component("registerTranslate")
public class RegisterTranslate {

    @Value("${com.fusionAuth.validation.password.tooShort.message}")
    String tooShortPassword;
    @Value("${com.fusionAuth.validation.password.tooLong.message}")
    String tooLongPassword;
    @Value("${com.fusionAuth.validation.password.singleCase.message}")
    String singleCasePassword;
    @Value("${com.fusionAuth.validation.password.requireNumber.message}")
    String requireNumberPassword;
    @Value("${com.fusionAuth.validation.password.invalid.message}")
    String invalidPassword;
    @Value("${com.fusionAuth.validation.email.duplicate.message}")
    String duplicateEmail;
    @Value("${com.fusionAuth.validation.email.notEmail.message}")
    String notEmail;
    @Value("${com.fusionAuth.validation.register.message}")
    String errorRegister;


    @Autowired
    @Qualifier("registerBOFactory")
    RegisterBOFactory registerBOFactory;

    public RegisterBO translate(ClientResponse<RegistrationResponse, Errors> data) {
        if (data.exception != null) {
            throw new RegisterException(errorRegister, data.exception);
        }
        if (data.wasSuccessful())
            return registerBOFactory.create(data.status, data.successResponse.user.id.toString(), data.successResponse.registration.id.toString());
        else {
            if (data.status == HttpStatus.BAD_REQUEST.value()) {
                Collection<List<Error>> errorsCollection = data.errorResponse.fieldErrors.values();
                for (Error err : errorsCollection.iterator().next()) {
                    switch (err.code) {
                        case "[tooShort]user.password":
                            throw new PasswordException(tooShortPassword);
                        case "[tooLong]user.password":
                            throw new PasswordException(tooLongPassword);
                        case "[singleCase]user.password":
                            throw new PasswordException(singleCasePassword);
                        case "[requireNumber]user.password":
                            throw new PasswordException(requireNumberPassword);
                        case "[duplicate]user.email":
                            throw new EmailException(duplicateEmail);
                        case "[notEmail]user.email":
                            throw new EmailException(notEmail);
                        default:
                            throw new RegisterException(err.code + err.message + "|" + errorRegister);
                    }
                }
            }

        }
        return registerBOFactory.create(data.status, null, null);
    }

}

