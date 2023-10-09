package ecommerce.mobile.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<CheckStatus, Integer> {

	private int[] allowedValues;

	@Override
	public void initialize(CheckStatus constraintAnnotation) {
		this.allowedValues = constraintAnnotation.allowedValues();
	}

	@Override
	public boolean isValid(Integer status, ConstraintValidatorContext context) {
		if (status == null) {
			return true; // or false based on your requirement
		}
		for (int allowedValue : allowedValues) {
			if (status == allowedValue) {
				return true;
			}
		}
		return false;
	}
}