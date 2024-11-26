package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

@Service
@RequiredArgsConstructor
public class QueryHandler {
    private static final int PAGE_SIZE = 3;
    private static final List<Sort.Order> SORT = List.of(asc("id"));

    private final InsurancePolicyRepositoryJpa repository;
    private final InsurancePolicyDetailsViewModelMapper detailsMapper = InsurancePolicyDetailsViewModelMapper.INSTANCE;
    private final InsurancePolicySummaryViewModelMapper summaryMapper = InsurancePolicySummaryViewModelMapper.INSTANCE;

    public InsurancePolicyDetailsViewModel detailsOf(long id) {
        return repository.findById(id)
                .map(detailsMapper::toViewModel)
                .orElseThrow(() -> new InsurancePolicyNotFoundException());
    }

    public InsurancePolicyListViewModel list(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(SORT))
                .withPage(page);

        Page<InsurancePolicyEntity> pagedResult = repository.findAll(pageable);
        long totalElements = pagedResult.getTotalElements();
        int totalPages = pagedResult.getTotalPages();

        List<InsurancePolicySummaryViewModel> insurancePolicies = pagedResult.stream()
                .map(summaryMapper::toViewModel)
                .toList();

        return InsurancePolicyListViewModel.builder()
                .insurancePolicies(insurancePolicies)
                .pageInfo(PageViewModel.builder()
                        .page(page)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .build())
                .build();
    }
}
