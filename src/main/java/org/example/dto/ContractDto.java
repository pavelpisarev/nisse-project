package org.example.dto;

import lombok.Data;
import org.example.model.ContractStatus;

import java.time.LocalDate;

@Data
public class ContractDto {
    private Long id;
    private String cypher;
    private ContractStatus contractStatus;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean originalInArchive;
    private Boolean forSite;
    private Long customerId;
    private Long supplierId;
}
