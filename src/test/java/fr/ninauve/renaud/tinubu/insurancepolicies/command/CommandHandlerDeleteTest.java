package fr.ninauve.renaud.tinubu.insurancepolicies.command;

import fr.ninauve.renaud.tinubu.insurancepolicies.exception.InsurancePolicyNotFoundException;
import fr.ninauve.renaud.tinubu.insurancepolicies.infra.db.InsurancePolicyRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static fr.ninauve.renaud.tinubu.insurancepolicies.TestData.INSURANCE_POLICY_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHandlerDeleteTest {
    @InjectMocks
    CommandHandler commandHandler;

    @Mock
    InsurancePolicyRepositoryJpa repository;

    @Test
    void should_delete() {
        when(repository.existsById(INSURANCE_POLICY_ID))
                .thenReturn(true);

        commandHandler.deleteInsurancePolicy(INSURANCE_POLICY_ID);

        verify(repository).deleteById(INSURANCE_POLICY_ID);
    }

    @Test
    void throw_if_update_with_unknown_id() {
        when(repository.existsById(INSURANCE_POLICY_ID))
                .thenReturn(false);

        assertThrows(InsurancePolicyNotFoundException.class, () -> commandHandler.deleteInsurancePolicy(INSURANCE_POLICY_ID));
    }
}