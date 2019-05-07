package com.alex.astraproject.shared.validators.impl;

import com.alex.astraproject.shared.validators.annotations.IsNotBothNull;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class IsNotBothNullValidator implements ConstraintValidator<IsNotBothNull, Object> {
	private String firstFieldName;
	private String secondFieldName;
	@Override
	public void initialize(IsNotBothNull constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
		try {
				Object firstValue = BeanUtils.getProperty(o, firstFieldName);
				Object secondValue = BeanUtils.getProperty(o, secondFieldName);

				return !(firstValue == null && secondValue == null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
}
