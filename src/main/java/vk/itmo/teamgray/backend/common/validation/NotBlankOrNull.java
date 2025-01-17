package vk.itmo.teamgray.backend.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

@ConstraintComposition(CompositionType.OR)
@Constraint(validatedBy = {})
@Null
@NotBlank
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankOrNull {
    String message() default "The value must be null or at least {value}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value() default 100;
}
