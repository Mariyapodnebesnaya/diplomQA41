package ru.netology.data.enums;

import lombok.Getter;

public enum Cards {
    APPROVED("4444 4444 4444 4441"),
    DECLINED("4444 4444 4444 4442"),
    NOT_EXIST_IN_DB("4444 4444 4444 4443"),
    MORE16("4444 5555 5555 55555");


    @Getter
    private String name;

    Cards(String name) {
        this.name = name;
    }
}
