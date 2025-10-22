package dev.tiagomendonca.sheerchat.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "A senha deve conter pelo menos uma letra, um número e um símbolo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
