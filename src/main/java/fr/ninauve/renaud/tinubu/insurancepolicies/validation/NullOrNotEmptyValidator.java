package fr.ninauve.renaud.tinubu.insurancepolicies.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, String> {
    @Override
    public void initialize(NullOrNotEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || StringUtils.isNotBlank(value);
    }
}
