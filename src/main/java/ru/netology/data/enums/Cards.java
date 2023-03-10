package ru.netology.data.enums;

import lombok.Getter;

public enum Cards {
    APPROVED("4444 4444 4444 4441"),
    DECLINED("4444 4444 4444 4442"),
    WRONG("4444 4444 4444 44443");

    @Getter
    private String name;

    Cards(String name) {
        this.name = name;
    }
}
