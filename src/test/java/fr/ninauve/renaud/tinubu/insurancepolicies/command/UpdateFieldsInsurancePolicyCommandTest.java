package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.INSURANCE_POLICY_ID;
import static org.assertj.core.api.Assertions.assertThat;

class UpdateFieldsInsurancePolicyCommandTest {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void succeed_when_all_null_except_id() {
        UpdateFieldsInsurancePolicyCommand command = UpdateFieldsInsurancePolicyCommand.builder()
                .id(INSURANCE_POLICY_ID)
                .build();

        Set<ConstraintViolation<UpdateFieldsInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).isEmpty();
    }

    @Test
    void fail_when_name_is_blank() {
        UpdateFieldsInsurancePolicyCommand command = UpdateFieldsInsurancePolicyCommand.builder()
                .id(INSURANCE_POLICY_ID)
                .name("")
                .build();

        Set<ConstraintViolation<UpdateFieldsInsurancePolicyCommand>> actual = validator.validate(command);
        assertThat(actual).hasSize(1);
    }
}