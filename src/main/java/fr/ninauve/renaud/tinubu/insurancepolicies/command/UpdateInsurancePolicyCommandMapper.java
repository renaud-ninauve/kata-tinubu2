package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UpdateInsurancePolicyCommandMapper {
    UpdateInsurancePolicyCommandMapper INSTANCE = Mappers.getMapper(UpdateInsurancePolicyCommandMapper.class);

    void copyFields(UpdateInsurancePolicyCommand command, @MappingTarget InsurancePolicyEntity entity);
}
