package com.cjj.qlemusic.security.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = LoginValidatorClass.class)
public @interface LoginValidator {
    int count() default 0;

    String message() default "login is not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
