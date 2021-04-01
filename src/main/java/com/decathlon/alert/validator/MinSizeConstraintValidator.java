package com.decathlon.alert.validator;

import com.decathlon.alert.dto.DeveloperRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MinSizeConstraintValidator implements ConstraintValidator<MinSizeConstraint, List<DeveloperRequest>> {

	@Override
	public boolean isValid(List<DeveloperRequest> values, ConstraintValidatorContext constraintValidatorContext) {
		return values != null && !values.isEmpty();
	}
}
