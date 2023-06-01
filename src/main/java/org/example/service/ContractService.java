package org.example.service;

import org.example.dto.ContractDto;
import org.example.model.Contract;
import org.example.model.ContractStatus;
import org.springframework.data.domain.Page;

public interface ContractService {
    Page<Contract> findFiltered(Long id, String cypher, ContractStatus contractStatus, String startDateBefore,
                                String startDateAfter, Boolean originalInArchive, Boolean forSite,
                                Long customerId, Long supplierId,
                                Integer page, Integer size);

    void deleteById(Long id);

    Contract updateById(Long id, ContractDto contractDto);

    Contract createContract(ContractDto contractDto);
}
