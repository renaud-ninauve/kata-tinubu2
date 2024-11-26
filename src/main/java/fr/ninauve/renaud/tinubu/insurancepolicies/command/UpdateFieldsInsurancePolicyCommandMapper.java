package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UpdateFieldsInsurancePolicyCommandMapper {
    UpdateFieldsInsurancePolicyCommandMapper INSTANCE = Mappers.getMapper(UpdateFieldsInsurancePolicyCommandMapper.class);

    void copyFields(UpdateFieldsInsurancePolicyCommand command, @MappingTarget InsurancePolicyEntity entity);
}
