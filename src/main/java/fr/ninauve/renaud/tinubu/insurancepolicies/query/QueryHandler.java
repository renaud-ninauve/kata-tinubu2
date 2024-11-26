package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryHandler {
    private static final int PAGE_SIZE = 3;

    private final InsurancePolicyRepositoryJpa repository;
    private final InsurancePolicyDetailsViewModelMapper detailsMapper = InsurancePolicyDetailsViewModelMapper.INSTANCE;
    private final InsurancePolicySummaryViewModelMapper summaryMapper = InsurancePolicySummaryViewModelMapper.INSTANCE;

    public InsurancePolicyDetailsViewModel detailsOf(long id) {
        return repository.findById(id)
                .map(detailsMapper::toViewModel)
                .orElseThrow(() -> new InsurancePolicyNotFoundException());
    }

    public InsurancePolicyListViewModel list(int page) {
        long totalSize = repository.count();
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id"))
                .withPage(page);
        List<InsurancePolicySummaryViewModel> insurancePolicies = repository.findAll(pageable).stream()
                .map(summaryMapper::toViewModel)
                .toList();

        return InsurancePolicyListViewModel.builder()
                .insurancePolicies(insurancePolicies)
                .pageInfo(PageViewModel.builder()
                        .page(page)
                        .size(totalSize)
                        .build())
                .build();
    }
}
