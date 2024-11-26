package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.validation.NullOrNotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UpdateFieldsInsurancePolicyCommand {
    @NotNull
    private Long id;
    @NullOrNotEmpty
    private String name;
    private InsurancePolicyStatus status;
    private Instant startDate;
    private Instant endDate;
}
