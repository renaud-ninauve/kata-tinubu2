package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsurancePolicySummaryViewModel {
    private final long id;
    private final String name;
}
