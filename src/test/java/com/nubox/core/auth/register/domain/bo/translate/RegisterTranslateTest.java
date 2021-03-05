package com.nubox.core.auth.register.domain.bo.translate;

import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import com.nubox.core.auth.register.data.common.exception.PasswordException;
import com.nubox.core.auth.register.data.common.exception.RegisterException;
import com.nubox.core.auth.register.domain.bo.RegisterBO;
import com.nubox.core.auth.register.domain.bo.factory.RegisterBOFactory;
import com.nubox.core.auth.register.util.Mocks;
import io.fusionauth.domain.api.user.RegistrationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class RegisterTranslateTest {

    @Spy
    private RegisterBOFactory registerBOFactory;

    @InjectMocks
    private RegisterTranslate registerTranslate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void translate_With_DataSuccessful() {

        //Given
        final UUID USER_REGISTRATION_ID = UUID.randomUUID();
        final UUID USER_ID = UUID.randomUUID();
        ClientResponse<RegistrationResponse, Errors> data = Mocks.getClientResponseOk(USER_REGISTRATION_ID, USER_ID);

        //When
        RegisterBO result = registerTranslate.translate(data);

        //Then
        then(result).isNotNull();
        then(result.getRegistrationId()).isEqualTo(USER_REGISTRATION_ID.toString());
        then(result.getUserId()).isEqualTo(USER_ID.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[tooShort]user.password", "[tooLong]user.password", "[singleCase]user.password",
            "[requireNumber]user.password", "[duplicate]user.email", "[notEmail]user.email", "other"})
    void translateData_With_Errors(String errorCode) {

        //Given
        ClientResponse<RegistrationResponse, Errors> data = Mocks.getClientResponseWithErrors(errorCode);

        //When - Then
        assertThrows(RuntimeException.class, () -> registerTranslate.translate(data));
    }
    @ParameterizedTest
    @ValueSource(strings = {
            "[tooShort]user.password", "[tooLong]user.password", "[singleCase]user.password",
            "[requireNumber]user.password", "[duplicate]user.email", "[notEmail]user.email", "other"})
    void translateData_With_ErrorsEmpty() {

        //Given
        ClientResponse<RegistrationResponse, Errors> data = Mocks.getClientResponseWithErrorsEmpty();

        //When - Then
        assertThrows(RuntimeException.class, () -> registerTranslate.translate(data));
    }

    @Test
    void translateData_With_Exception() {

        //Given
        ClientResponse<RegistrationResponse, Errors> data = new ClientResponse<>();
        data.exception = new NoSuchElementException();

        //When - Then
        assertThrows(RegisterException.class, () -> registerTranslate.translate(data));
    }

    @Test
    void translateData_With_Status500() {

        //Given
        ClientResponse<RegistrationResponse, Errors> data = new ClientResponse<>();
        data.status = 500;

        //When
        RegisterBO result = registerTranslate.translate(data);

        //Then
        then(result).isNotNull();
        then(result.getStatus()).isEqualTo(500);
        then(result.getUserId()).isNull();
    }

}