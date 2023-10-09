package ecommerce.mobile.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
public @interface CheckStatus {
	String message() default "Invalid status value only (0, 1, 2)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int[] allowedValues() default {};

}