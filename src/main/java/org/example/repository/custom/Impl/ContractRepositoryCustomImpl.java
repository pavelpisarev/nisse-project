package org.example.repository.custom.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.*;
import org.example.model.Contract;
import org.example.model.ContractStatus;
import org.example.repository.custom.ContractRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ContractRepositoryCustomImpl implements ContractRepositoryCustom {
    @Autowired
    EntityManager entityManager;

    @Override
    public Page<Contract> findAllFiltered(Long id, String cypher, ContractStatus contractStatus, String startDateBefore,
                                          String startDateAfter, Boolean originalInArchive,
                                          Boolean forSite, Long customerId, Long supplierId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contract> contractCriteriaQuery = criteriaBuilder.createQuery(Contract.class);
        Root<Contract> contractRoot = contractCriteriaQuery.from(Contract.class);
        List<Predicate> predicates = new ArrayList<>();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (id != null) {
            predicates.add(criteriaBuilder.equal(contractRoot.get("id"), id));
        }
        if (cypher != null) {
            predicates.add(criteriaBuilder.like(contractRoot.get("cypher"), "%" + cypher + "%"));
        }
        if (contractStatus != null) {
            predicates.add(criteriaBuilder.equal(contractRoot.get("contractStatus"), contractStatus));
        }
        if (startDateBefore != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(contractRoot.get("startDate"), LocalDate.parse(startDateBefore, pattern)));
        }
        if (startDateAfter != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(contractRoot.get("startDate"), LocalDate.parse(startDateAfter, pattern)));
        }
        if (supplierId != null) {
            Subquery<Long> subquery = contractCriteriaQuery.subquery(Long.class);
            Root<Contract> subqueryContract = subquery.from(Contract.class);
            subquery.select(subqueryContract.get("id"))
                .where(criteriaBuilder.equal(subqueryContract.get("supplier").get("id"), supplierId));
            predicates.add(criteriaBuilder.in(contractRoot.get("id")).value(subquery));
        }
        if (customerId != null) {
            Subquery<Long> subquery = contractCriteriaQuery.subquery(Long.class);
            Root<Contract> subqueryContract = subquery.from(Contract.class);
            subquery.select(subqueryContract.get("id"))
                .where(criteriaBuilder.equal(subqueryContract.get("customer").get("id"), customerId));
            predicates.add(criteriaBuilder.in(contractRoot.get("id")).value(subquery));
        }
        if (originalInArchive != null) {
            predicates.add(criteriaBuilder.equal(contractRoot.get("originalInArchive"), originalInArchive));
        }
        if (forSite != null) {
            predicates.add(criteriaBuilder.equal(contractRoot.get("forSite"), forSite));
        }
        contractCriteriaQuery.where(predicates.toArray(new Predicate[0]));
        Page<Contract> result = new PageImpl<>(entityManager.createQuery(contractCriteriaQuery).getResultList());
        if (result.getTotalElements()==0) throw new EntityNotFoundException();
        return result;
    }
}
