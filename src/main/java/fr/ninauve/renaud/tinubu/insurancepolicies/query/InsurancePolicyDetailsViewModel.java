package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import fr.ninauve.renaud.tinubu.insurancepolicies.command.InsurancePolicyStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class InsurancePolicyDetailsViewModel {
    private Long id;
    private String name;
    private InsurancePolicyStatus status;
    private Instant startDate;
    private Instant endDate;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
