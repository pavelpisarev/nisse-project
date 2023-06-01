package org.example;

import org.example.model.Contract;
import org.example.model.ContractStatus;
import org.example.model.LegalEntity;
import org.example.repository.ContractRepository;
import org.example.repository.LegalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DbInitializer implements CommandLineRunner {
    @Autowired
    LegalEntityRepository legalEntityRepository;

    @Autowired
    ContractRepository contractRepository;

    @Override
    public void run(String... args) throws Exception {
        LegalEntity l1 = LegalEntity.builder()
            .fullName("Иванов Иван Иванович")
            .shortName("Иван И.")
            .postAddress("Московская 21")
            .factAddress("Пушкина 9")
            .inn("1234743214")
            .build();

        LegalEntity l2 = LegalEntity.builder()
            .fullName("Петров Петр Петрович")
            .shortName("Петр П.")
            .postAddress("Рязанский вал 21")
            .factAddress("Львовская 3")
            .inn("4214743234")
            .build();

        LegalEntity l3 = LegalEntity.builder()
            .fullName("Павлов Павел Павлович")
            .shortName("Павел П.")
            .postAddress("Угловая 56")
            .factAddress("Кунцевская 3")
            .inn("6694739766")
            .build();

        LegalEntity l4 = LegalEntity.builder()
            .fullName("Сергеев Сергей Сергеевич")
            .shortName("Сергей С.")
            .postAddress("Чайная 8")
            .factAddress("Разумовского 29")
            .inn("6694739766")
            .build();

        List<LegalEntity> legalEntities = List.of(l1, l2, l3, l4);
        legalEntityRepository.saveAll(legalEntities);

        List<Contract> contracts = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ContractStatus[] contractStatuses = ContractStatus.values();
            Contract contract = Contract.builder()
                .cypher("cypher" + i)
                .contractStatus(contractStatuses[new Random().nextInt(contractStatuses.length)])
                .contractNumber(String.valueOf(Math.abs(new Random().nextInt())))
                .startDate(LocalDate.now().minusYears(new Random().nextInt(10)))
                .endDate(LocalDate.now()
                    .plusYears(new Random().nextInt(5)).
                    plusMonths(new Random().nextInt(12)))
                .customer(legalEntities.get(new Random().nextInt(legalEntities.size())))
                .supplier(legalEntities.get(new Random().nextInt(legalEntities.size())))
                .theme("theme" + i)
                .contractSum(new BigDecimal(new Random().nextInt(999999)))
                .description("Description" + i)
                .ndsPercent(new Random().nextInt(4))
                .advanceSum(new BigDecimal(new Random().nextInt(999999)))
                .contractGuaranteeNeeded(new Random().nextBoolean())
                .contractGuaranteeSum(new BigDecimal(new Random().nextInt(999999)))
                .contractGuaranteeCommission(new BigDecimal(new Random().nextInt(999999)))
                .warrantyGuaranteeNeeded(new Random().nextBoolean())
                .warrantyGuaranteeEndDate(LocalDate.now().plusYears(new Random().nextInt(5)))
                .warrantyGuaranteeSum(new BigDecimal(new Random().nextInt(999999)))
                .warrantyGuaranteeCommission(new BigDecimal(new Random().nextInt(999999)))
                .originalInArchive(new Random().nextBoolean())
                .forSite(new Random().nextBoolean())
                .build();

            contractRepository.save(contract);
        }
    }
}
