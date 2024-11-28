CREATE TABlE insurance_policies (
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    status              VARCHAR(8) NOT NULL,
    start_date          TIMESTAMP,
    end_date            TIMESTAMP,
    created_date        TIMESTAMP NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL
);

CREATE SEQUENCE insurance_policies_seq INCREMENT 5;