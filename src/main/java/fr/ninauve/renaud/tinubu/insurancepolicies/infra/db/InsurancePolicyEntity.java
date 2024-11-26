package fr.ninauve.renaud.tinubu.insurancepolicies.infra.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "insurance_policies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class InsurancePolicyEntity {

    public enum Status {
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_policies_seq")
    @SequenceGenerator(
            name = "insurance_policies_seq",
            allocationSize = 5
    )
    private Long id;

    @Column
    private String name;

    @Column
    private Status status;

    @Column
    private Instant startDate;

    @Column
    private Instant endDate;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)

    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
