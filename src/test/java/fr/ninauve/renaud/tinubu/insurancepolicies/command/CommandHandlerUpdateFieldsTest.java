package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.TestData;
import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHandlerUpdateFieldsTest {
    private static final String UPDATE_NAME = "update name";

    @InjectMocks
    CommandHandler commandHandler;

    @Mock
    InsurancePolicyRepositoryJpa repository;

    @Test
    void should_update() {
        when(repository.findById(INSURANCE_POLICY_ID))
                .thenReturn(Optional.of(InsurancePolicyEntity.builder()
                        .id(INSURANCE_POLICY_ID)
                        .name(TestData.INSURANCE_POLICY_NAME)
                        .status(InsurancePolicyEntity.Status.ACTIVE)
                        .startDate(INSURANCE_POLICY_START_DATE)
                        .endDate(INSURANCE_POLICY_END_DATE)
                        .build()));

        UpdateFieldsInsurancePolicyCommand command = UpdateFieldsInsurancePolicyCommand.builder()
                .id(TestData.INSURANCE_POLICY_ID)
                .name(UPDATE_NAME)
                .build();

        commandHandler.updateFieldsInsurancePolicy(command);

        verify(repository).save(InsurancePolicyEntity.builder()
                .id(TestData.INSURANCE_POLICY_ID)
                .name(UPDATE_NAME)
                .status(InsurancePolicyEntity.Status.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build());
    }

    @Test
    void throw_if_update_with_unknown_id() {
        when(repository.findById(INSURANCE_POLICY_ID))
                .thenReturn(Optional.empty());

        UpdateFieldsInsurancePolicyCommand command = UpdateFieldsInsurancePolicyCommand.builder()
                .id(TestData.INSURANCE_POLICY_ID)
                .name(UPDATE_NAME)
                .build();

        assertThrows(InsurancePolicyNotFoundException.class, () -> commandHandler.updateFieldsInsurancePolicy(command));
    }
}