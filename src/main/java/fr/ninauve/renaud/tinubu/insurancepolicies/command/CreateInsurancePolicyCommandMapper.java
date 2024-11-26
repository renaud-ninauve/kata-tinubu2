package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateInsurancePolicyCommandMapper {
    CreateInsurancePolicyCommandMapper INSTANCE = Mappers.getMapper(CreateInsurancePolicyCommandMapper.class);

    InsurancePolicyEntity toEntity(CreateInsurancePolicyCommand command);
}
