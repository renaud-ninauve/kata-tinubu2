package fr.ninauve.renaud.tinubu.insurancepolicies.infra.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InsurancePolicyRepositoryJpa extends CrudRepository<InsurancePolicyEntity, Long>,
        PagingAndSortingRepository<InsurancePolicyEntity, Long> {
}
