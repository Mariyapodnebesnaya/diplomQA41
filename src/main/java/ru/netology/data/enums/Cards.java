package ru.netology.data.enums;

import lombok.Getter;

public enum Cards {
    APPROVED("4444 4444 4444 4441"),
    DECLINED("4444 4444 4444 4442"),
    NOT_EXIST_IN_DB("4444 4444 4444 4443");

    @Getter
    private String name;

    Cards(String name) {
        this.name = name;
    }
}
