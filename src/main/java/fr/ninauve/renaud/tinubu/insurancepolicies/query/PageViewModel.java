package fr.ninauve.renaud.tinubu.insurancepolicies.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageViewModel {
    private final int page;
    private final long size;
}
