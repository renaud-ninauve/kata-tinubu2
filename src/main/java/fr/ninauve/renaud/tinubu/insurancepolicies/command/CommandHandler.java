package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommandHandler {
    private final InsurancePolicyRepositoryJpa repository;
    private final CreateInsurancePolicyCommandMapper createMapper = CreateInsurancePolicyCommandMapper.INSTANCE;
    private final UpdateInsurancePolicyCommandMapper updateMapper = UpdateInsurancePolicyCommandMapper.INSTANCE;

    public long createInsurancePolicy(CreateInsurancePolicyCommand command) {
        InsurancePolicyEntity insurancePolicy = createMapper.toEntity(command);
        InsurancePolicyEntity saved = save(insurancePolicy);
        return saved.getId();
    }

    @Transactional
    public void updateInsurancePolicy(UpdateInsurancePolicyCommand command) {
        if (!repository.existsById(command.getId())) {
            throw new InsurancePolicyNotFoundException();
        }

        InsurancePolicyEntity insurancePolicy = updateMapper.toEntity(command);
        repository.save(insurancePolicy);
    }

    @Transactional
    public void deleteInsurancePolicy(long id) {
        if (!repository.existsById(id)) {
            throw new InsurancePolicyNotFoundException();
        }

        repository.deleteById(id);
    }

    @Transactional
    private InsurancePolicyEntity save(InsurancePolicyEntity insurancePolicyEntity) {
        return repository.save(insurancePolicyEntity);
    }
}
