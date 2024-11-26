package fr.ninauve.renaud.tinubu.insurancepolicies.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NullOrNotEmptyValidatorTest {

    @Test
    void null_is_valid() {
        boolean actual = new NullOrNotEmptyValidator().isValid(null, null);
        assertTrue(actual);
    }

    @Test
    void empty_is_invalid() {
        boolean actual = new NullOrNotEmptyValidator().isValid("", null);
        assertFalse(actual);
    }

    @Test
    void not_empty_is_invalid() {
        boolean actual = new NullOrNotEmptyValidator().isValid("not empty", null);
        assertTrue(actual);
    }
}