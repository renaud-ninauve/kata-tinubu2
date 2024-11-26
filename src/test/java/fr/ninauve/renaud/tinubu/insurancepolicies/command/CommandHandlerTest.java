package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.TestData;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.INSURANCE_POLICY_END_DATE;
import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.INSURANCE_POLICY_START_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {
    @InjectMocks
    CommandHandler commandHandler;

    @Mock
    InsurancePolicyRepositoryJpa repository;

    @Test
    void should_create() {
        long savedId = 777L;

        when(repository.save(any()))
                .thenReturn(InsurancePolicyEntity.builder()
                        .id(savedId)
                        .build());

        CreateInsurancePolicyCommand command = CreateInsurancePolicyCommand.builder()
                .name(TestData.INSURANCE_POLICY_NAME)
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build();

        long actual = commandHandler.createInsurancePolicy(command);

        verify(repository).save(InsurancePolicyEntity.builder()
                .name(TestData.INSURANCE_POLICY_NAME)
                .status(InsurancePolicyEntity.Status.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build());

        assertThat(actual).isEqualTo(savedId);
    }
}