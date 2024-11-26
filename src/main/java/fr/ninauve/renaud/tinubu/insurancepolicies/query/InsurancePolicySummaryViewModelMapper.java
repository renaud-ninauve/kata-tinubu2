package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsurancePolicySummaryViewModelMapper {
    InsurancePolicySummaryViewModelMapper INSTANCE = Mappers.getMapper(InsurancePolicySummaryViewModelMapper.class);

    InsurancePolicySummaryViewModel toViewModel(InsurancePolicyEntity entity);
}
