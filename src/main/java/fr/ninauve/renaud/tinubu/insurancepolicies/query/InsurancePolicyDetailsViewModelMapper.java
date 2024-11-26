package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsurancePolicyDetailsViewModelMapper {
    InsurancePolicyDetailsViewModelMapper INSTANCE = Mappers.getMapper(InsurancePolicyDetailsViewModelMapper.class);

    InsurancePolicyDetailsViewModel toViewModel(InsurancePolicyEntity entity);
}
