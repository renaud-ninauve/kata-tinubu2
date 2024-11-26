package fr.ninauve.renaud.tinubu.insurancepolicies.infra.db;

import org.springframework.data.repository.CrudRepository;

public interface InsurancePolicyRepositoryJpa extends CrudRepository<InsurancePolicyEntity, Long> {
}
