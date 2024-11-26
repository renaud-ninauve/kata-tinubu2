package fr.ninauve.renaud.tinubu.insurancepolicies;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestData {
    public static final long INSURANCE_POLICY_ID = 1234;
    public static final String INSURANCE_POLICY_JSON_ID = "" + INSURANCE_POLICY_ID;
    public static final String INSURANCE_POLICY_NAME = "my-policy";
    public static final String INSURANCE_POLICY_JSON_STATUS = "ACTIVE";
    public static final String INSURANCE_POLICY_JSON_START_DATE = "2024-11-24T14:41:52.123456Z";
    public static final Instant INSURANCE_POLICY_START_DATE = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(INSURANCE_POLICY_JSON_START_DATE));
    public static final String INSURANCE_POLICY_JSON_END_DATE = "2025-11-24T14:41:52.123456Z";
    public static final Instant INSURANCE_POLICY_END_DATE = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(INSURANCE_POLICY_JSON_END_DATE));

}
