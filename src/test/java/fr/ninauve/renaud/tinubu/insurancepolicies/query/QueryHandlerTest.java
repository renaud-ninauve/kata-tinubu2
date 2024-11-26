package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.TestData;
import fr.ninauve.renaud.tinubu.insurancepolicies.command.InsurancePolicyStatus;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryHandlerTest {
    @InjectMocks
    QueryHandler queryHandler;

    @Mock
    InsurancePolicyRepositoryJpa repository;

    @Test
    void return_details() {
        when(repository.findById(INSURANCE_POLICY_ID))
                .thenReturn(Optional.of(InsurancePolicyEntity.builder()
                        .id(INSURANCE_POLICY_ID)
                        .name(TestData.INSURANCE_POLICY_NAME)
                        .status(InsurancePolicyEntity.Status.ACTIVE)
                        .startDate(INSURANCE_POLICY_START_DATE)
                        .endDate(INSURANCE_POLICY_END_DATE)
                        .createdDate(INSURANCE_POLICY_CREATED_DATE)
                        .lastModifiedDate(INSURANCE_POLICY_MODIFIED_DATE)
                        .build()));

        InsurancePolicyDetailsViewModel actual = queryHandler.detailsOf(INSURANCE_POLICY_ID);

        assertThat(actual).isEqualTo(InsurancePolicyDetailsViewModel.builder()
                .id(INSURANCE_POLICY_ID)
                .name(TestData.INSURANCE_POLICY_NAME)
                .status(InsurancePolicyStatus.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .createdDate(INSURANCE_POLICY_CREATED_DATE)
                .lastModifiedDate(INSURANCE_POLICY_MODIFIED_DATE)
                .build());
    }

    @Test
    void throw_if_id_is_unknown() {
        when(repository.findById(INSURANCE_POLICY_ID))
                .thenReturn(Optional.empty());

        assertThrows(InsurancePolicyNotFoundException.class, () -> queryHandler.detailsOf(INSURANCE_POLICY_ID));
    }
}