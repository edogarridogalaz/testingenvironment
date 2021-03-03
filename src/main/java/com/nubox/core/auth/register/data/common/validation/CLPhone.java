package com.nubox.core.auth.register.data.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Realiza validación de telefono en formato Chileno.
 * Ej: +56981807560
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CLPhoneValidator.class)
@Documented
public @interface CLPhone {

    String message() default "{com.nubox.validation.ChileanPhone.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}