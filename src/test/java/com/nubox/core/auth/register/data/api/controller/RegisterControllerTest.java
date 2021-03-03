package com.nubox.core.auth.register.data.api.controller;

import com.nubox.core.auth.register.data.common.exception.RestExceptionHandler;
import com.nubox.core.auth.register.data.model.data.business.Register;
import com.nubox.core.auth.register.data.model.request.UserRequest;
import com.nubox.core.auth.register.data.model.response.RegisterResponse;
import com.nubox.core.auth.register.data.service.RegisterService;
import com.nubox.core.auth.register.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    private static final String PATH = "/auth/register";

    private MockMvc mockMvc;

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(registerController)
                .setControllerAdvice(RestExceptionHandler.class)
                .alwaysDo(print())
                .build();
    }

    @Test
    void createRegister_Ok() throws Exception {

        //Given
        UserRequest request = new UserRequest();
        request.setEmail("test@nubox.com");
        request.setFullName("name");
        request.setPhone(null);
        request.setPassword("******");

        final String USER_ID = "user-id";
        final String REGISTRATION_ID = "registration-id";
        Register registerMock = new Register();
        registerMock.setUserId(USER_ID);
        registerMock.setRegistrationId(REGISTRATION_ID);

        given(registerService.create(any(UserRequest.class))).willReturn(new RegisterResponse(registerMock));

        //When
        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(request)))

        //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.register.userId").value(USER_ID))
                .andExpect(jsonPath("$.register.registrationId").value(REGISTRATION_ID));
    }

    @Test
    void createRegister_Expect_BadRequest() throws Exception {

        //Given
        UserRequest request = new UserRequest();
        request.setEmail("testnubox.com");
        request.setPhone("232323");

        //When
        mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.asJsonString(request)))

        //Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()").value(4));
    }

}