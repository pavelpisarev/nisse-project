package org.example.repository.custom;

import org.example.model.Contract;
import org.example.model.ContractStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractRepositoryCustom {
    Page<Contract> findAllFiltered(Long id, String cypher, ContractStatus contractStatus,
                                   String startDateBefore, String startDateAfter,
                                   Boolean originalInArchive, Boolean forSite,
                                   Long customerId, Long supplierId,
                                   Pageable pageable);
}
