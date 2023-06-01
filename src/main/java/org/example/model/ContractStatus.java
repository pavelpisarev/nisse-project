package org.example.model;

import lombok.Getter;

public enum ContractStatus {
    DRAFT("Проект договора"),
    PROGRESS("Исполнение"),
    PAUSE("Приостановлен"),
    WARRANTY("Закрыт (гарантия)"),
    VOID("Аннулирован"),
    FINISH("Закрыт");

    @Getter
    private String displayName;

    ContractStatus(String displayName) {
        this.displayName = displayName;
    }
}
