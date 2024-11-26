package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class UpdateInsurancePolicyCommandTest {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void succeed_when_all_fields_are_correct() {
        UpdateInsurancePolicyCommand command = UpdateInsurancePolicyCommand.builder()
                .id(INSURANCE_POLICY_ID)
                .name(INSURANCE_POLICY_NAME)
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        Set<ConstraintViolation<UpdateInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).isEmpty();
    }

    @Test
    void fail_when_name_is_null() {
        UpdateInsurancePolicyCommand command = UpdateInsurancePolicyCommand.builder()
                .id(INSURANCE_POLICY_ID)
                .name(null)
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        Set<ConstraintViolation<UpdateInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).hasSize(1);
    }

    @Test
    void fail_when_name_is_blank() {
        UpdateInsurancePolicyCommand command = UpdateInsurancePolicyCommand.builder()
                .id(INSURANCE_POLICY_ID)
                .name("")
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        Set<ConstraintViolation<UpdateInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).hasSize(1);
    }
}