package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.ContractDto;
import org.example.model.Contract;
import org.example.model.ContractStatus;
import org.example.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contracts", produces = "application/json")
@Tag(name = "Contract controller", description = "Поиск, создание контрактов")
public class ContractController {
    @Autowired
    ContractService contractService;

    @Operation(summary = "Вывод списка контрактов. Без фильтров выводит все контракты постранично. Размер страницы по умолчанию - 10")
    @GetMapping
    public Page<Contract> getAllFiltered(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String cypher,
        @RequestParam(required = false) ContractStatus contractStatus,
        @Parameter(description = "В формате dd-MM-yyyy")
        @RequestParam(required = false) String startDateBefore,
        @Parameter(description = "В формате dd-MM-yyyy")
        @RequestParam(required = false) String startDateAfter,
        @RequestParam(required = false) Boolean originalInArchive,
        @RequestParam(required = false) Boolean forSite,
        @RequestParam(required = false) Long customerId,
        @RequestParam(required = false) Long supplierId,
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return contractService.findFiltered(id, cypher, contractStatus, startDateBefore, startDateAfter, originalInArchive,
            forSite, customerId, supplierId, page, size);
    }

    @Operation(summary = "Создание нового контракта")
    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody ContractDto contractDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contractService.createContract(contractDto));
    }

    @Operation(summary = "Обновление контракта по id")
    @PutMapping("{id}")
    public Contract updateById(@PathVariable Long id, @RequestBody ContractDto contractDto) {
        return contractService.updateById(id, contractDto);
    }

    @Operation(summary = "Удаление контракта по id")
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        contractService.deleteById(id);
    }
}
