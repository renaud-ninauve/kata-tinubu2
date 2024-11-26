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

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHandlerUpdateTest {
    private static final String UPDATE_NAME = "update name";
    private static final String UPDATE_JSON_START_DATE = "2334-11-24T14:41:52.123456Z";
    private static final Instant UPDATE_START_DATE = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(UPDATE_JSON_START_DATE));
    private static final String UPDATE_JSON_END_DATE = "2555-11-24T14:41:52.123456Z";
    private static final Instant UPDATE_END_DATE = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(UPDATE_JSON_END_DATE));

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

        UpdateInsurancePolicyCommand command = UpdateInsurancePolicyCommand.builder()
                .id(TestData.INSURANCE_POLICY_ID)
                .name(UPDATE_NAME)
                .status(InsurancePolicyStatus.INACTIVE)
                .startDate(UPDATE_START_DATE)
                .endDate(UPDATE_END_DATE)
                .build();

        commandHandler.updateInsurancePolicy(command);

        verify(repository).save(InsurancePolicyEntity.builder()
                .id(TestData.INSURANCE_POLICY_ID)
                .name(UPDATE_NAME)
                .status(InsurancePolicyEntity.Status.INACTIVE)
                .startDate(UPDATE_START_DATE)
                .endDate(UPDATE_END_DATE)
                .build());
    }

    @Test
    void throw_if_update_with_unknown_id() {
        when(repository.findById(INSURANCE_POLICY_ID))
                .thenReturn(Optional.empty());

        UpdateInsurancePolicyCommand command = UpdateInsurancePolicyCommand.builder()
                .id(TestData.INSURANCE_POLICY_ID)
                .name(UPDATE_NAME)
                .status(InsurancePolicyStatus.INACTIVE)
                .startDate(UPDATE_START_DATE)
                .endDate(UPDATE_END_DATE)
                .build();

        assertThrows(InsurancePolicyNotFoundException.class, () -> commandHandler.updateInsurancePolicy(command));
    }
}