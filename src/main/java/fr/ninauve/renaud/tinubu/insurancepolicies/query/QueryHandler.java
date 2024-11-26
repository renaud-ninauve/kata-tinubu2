package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryHandler {
    private final InsurancePolicyRepositoryJpa repository;
    private final InsurancePolicyDetailsViewModelMapper detailsMapper = InsurancePolicyDetailsViewModelMapper.INSTANCE;

    public InsurancePolicyDetailsViewModel detailsOf(long id) {
        return repository.findById(id)
                .map(detailsMapper::toViewModel)
                .orElseThrow(() -> new InsurancePolicyNotFoundException());
    }
}
