package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class CreateInsurancePolicyCommandTest {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void succeed_when_all_fields_are_correct() {
        CreateInsurancePolicyCommand command = CreateInsurancePolicyCommand.builder()
                .name(INSURANCE_POLICY_NAME)
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        Set<ConstraintViolation<CreateInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).isEmpty();
    }

    @Test
    void fail_when_name_is_null() {
        CreateInsurancePolicyCommand command = CreateInsurancePolicyCommand.builder()
                .name(null)
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        Set<ConstraintViolation<CreateInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).hasSize(1);
    }

    @Test
    void fail_when_name_is_blank() {
        CreateInsurancePolicyCommand command = CreateInsurancePolicyCommand.builder()
                .name("")
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        Set<ConstraintViolation<CreateInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).hasSize(1);
    }
}