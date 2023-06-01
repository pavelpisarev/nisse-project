package org.example.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dto.ContractDto;
import org.example.model.Contract;
import org.example.model.ContractStatus;
import org.example.model.LegalEntity;
import org.example.repository.ContractRepository;
import org.example.repository.LegalEntityRepository;
import org.example.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractRepository contractRepository;

    @Autowired
    LegalEntityRepository legalEntityRepository;

    @Override
    public Page<Contract> findFiltered(Long id, String cypher, ContractStatus contractStatus, String startDateBefore,
                                       String startDateAfter, Boolean originalInArchive, Boolean forSite,
                                       Long customerId, Long supplierId,
                                       Integer page, Integer size) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Pageable pageable = PageRequest.of(page, size);
        return contractRepository.findAllFiltered(id, cypher, contractStatus, startDateBefore, startDateAfter,
            originalInArchive, forSite, customerId, supplierId, pageable);
    }

    @Override
    public void deleteById(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public Contract updateById(Long id, ContractDto contractDto) {
        Contract contract = contractRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (contractDto.getCypher() != null) contract.setCypher(contractDto.getCypher());
        if (contractDto.getContractStatus() != null) contract.setContractStatus(contractDto.getContractStatus());
        if (contractDto.getStartDate() != null) contract.setStartDate(contractDto.getStartDate());
        if (contractDto.getEndDate() != null) contract.setEndDate(contractDto.getEndDate());
        if (contractDto.getOriginalInArchive() != null)
            contract.setOriginalInArchive(contractDto.getOriginalInArchive());
        if (contractDto.getForSite() != null) contract.setForSite(contractDto.getForSite());
        if (contractDto.getSupplierId() != null)
            contract.setSupplier(legalEntityRepository.findById(contractDto.getSupplierId()).orElseThrow(EntityNotFoundException::new));
        if (contractDto.getCustomerId() != null)
            contract.setCustomer(legalEntityRepository.findById(contractDto.getCustomerId()).orElseThrow(EntityNotFoundException::new));
        return contractRepository.save(contract);
    }

    @Override
    public Contract createContract(ContractDto contractDto) {
        LegalEntity customer = legalEntityRepository.findById(contractDto.getCustomerId()).orElseThrow(EntityNotFoundException::new);
        LegalEntity supplier = legalEntityRepository.findById(contractDto.getSupplierId()).orElseThrow(EntityNotFoundException::new);
        List<LegalEntity> legalEntities = legalEntityRepository.findAll();
        Contract contract = Contract.builder()
            .supplier(customer)
            .customer(supplier)
            .cypher(contractDto.getCypher())
            .contractStatus(contractDto.getContractStatus())
            .startDate(contractDto.getStartDate())
            .endDate(contractDto.getEndDate())
            .originalInArchive(contractDto.getOriginalInArchive())
            .forSite(contractDto.getForSite())
            .build();
        return contractRepository.save(contract);
    }

}
