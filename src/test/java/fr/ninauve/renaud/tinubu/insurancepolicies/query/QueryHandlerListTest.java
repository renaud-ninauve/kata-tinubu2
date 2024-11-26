package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.*;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryHandlerListTest {
    @InjectMocks
    QueryHandler queryHandler;

    @Mock
    InsurancePolicyRepositoryJpa repository;
    @Mock
    Page<InsurancePolicyEntity> insuranceEntityPage;

    @Test
    void list() {
        List<InsurancePolicyEntity> entities = of(InsurancePolicyEntity.builder()
                .id(INSURANCE_POLICY_ID)
                .name(INSURANCE_POLICY_NAME)
                .status(InsurancePolicyEntity.Status.ACTIVE)
                .startDate(INSURANCE_POLICY_START_DATE)
                .endDate(INSURANCE_POLICY_END_DATE)
                .build());

        long totalElements = 100L;
        int totalPages = 12;
        int page = 4;

        when(insuranceEntityPage.getTotalElements()).thenReturn(totalElements);
        when(insuranceEntityPage.getTotalPages()).thenReturn(totalPages);
        when(insuranceEntityPage.stream()).thenReturn(entities.stream());

        when(repository.findAll(any(Pageable.class))).thenReturn(insuranceEntityPage);

        InsurancePolicyListViewModel actual = queryHandler.list(page);

        assertThat(actual).isEqualTo(InsurancePolicyListViewModel.builder()
                .insurancePolicies(of(InsurancePolicySummaryViewModel.builder()
                        .id(INSURANCE_POLICY_ID)
                        .name(INSURANCE_POLICY_NAME)
                        .build()))
                .pageInfo(PageViewModel.builder()
                        .page(page)
                        .totalElements(totalElements)
                        .totalPages(12L)
                        .build())
                .build());
    }

    @Test
    void throw_if_id_is_unknown() {
        when(repository.findById(INSURANCE_POLICY_ID))
                .thenReturn(Optional.empty());

        assertThrows(InsurancePolicyNotFoundException.class, () -> queryHandler.detailsOf(INSURANCE_POLICY_ID));
    }
}