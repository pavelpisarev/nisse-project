package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 64)
    private String cypher;

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    @Column(length = 255)
    private String contractNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer", nullable = false)
    @ToString.Exclude
    private LegalEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier", nullable = false)
    @ToString.Exclude
    private LegalEntity supplier;

    @Column(length = 4000, nullable = false)
    private String theme;

    private BigDecimal contractSum;

    @Column(length = 12000)
    private String description;

    private Integer ndsPercent;

    private BigDecimal advanceSum;

    private Boolean contractGuaranteeNeeded;

//    @Enumerated(EnumType.STRING)
//    private GuaranteeType contractGuaranteeType;

    private BigDecimal contractGuaranteeSum;

    private BigDecimal contractGuaranteeCommission;

    private Boolean warrantyGuaranteeNeeded;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate warrantyGuaranteeEndDate;

//    @Enumerated(EnumType.STRING)
//    private GuaranteeType warrantyGuaranteeType;

    private BigDecimal warrantyGuaranteeSum;

    private BigDecimal warrantyGuaranteeCommission;

    @Column(length = 6000)
    private String suppliersRestrictions; //условия привлечения соисполнителей. сюда же иные примечания

    @Column(length = 2000)
    private String url;

    @Column(length = 100)
    private String contactFIO;

    @Column(length = 50)
    private String contactPosition;

    @Column(length = 50)
    private String contactPhone;

    @Column(length = 50)
    private String contactEmail;

    private Boolean originalInArchive; //оригинал в архиве
    private Boolean forSite; //показывать на сайте
}
