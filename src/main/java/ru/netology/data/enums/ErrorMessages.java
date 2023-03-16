package ru.netology.data.enums;

import lombok.Getter;

public enum ErrorMessages {
    WRONG_FORMAT("Неверный формат"),
    REQUIRED_FIELD("Поле обязательно для заполнения"),
    INVALID_CARD_EXPIRATION_DATE("Неверно указан срок действия карты"),
    CARD_EXPIRED("Истёк срок действия карты");

    @Getter
    private String text;

    ErrorMessages(String text) {
        this.text = text;
    }
}
