package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InsurancePolicyListViewModel {
    private final List<InsurancePolicySummaryViewModel> insurancePolicies;
    private final PageViewModel pageInfo;
}
