package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "legal_entities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LegalEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 1000)
    private String fullName;

    @Column(length = 500)
    private String shortName;

    @Column(length = 1000)
    private String postAddress;

    @Column(length = 1000)
    private String factAddress;

    private String inn;
}
