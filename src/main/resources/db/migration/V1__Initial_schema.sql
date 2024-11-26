CREATE TABlE insurance_policies (
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    status              VARCHAR(8) NOT NULL,
    start_date          TIMESTAMP NOT NULL,
    end_date            TIMESTAMP NOT NULL,
    created_date        TIMESTAMP NOT NULL,
    last_modified_date  TIMESTAMP NOT NULL
);

CREATE SEQUENCE insurance_policies_seq INCREMENT 5;