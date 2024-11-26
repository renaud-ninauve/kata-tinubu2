package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UpdateInsurancePolicyCommand {
    @NotNull
    private Long id;

    @NotBlank(message = "name should be a not empty string")
    private String name;

    @NotNull(message = "status should be one of ACTIVE, INACTIVE")
    private InsurancePolicyStatus status;

    private Instant startDate;
    private Instant endDate;
}
