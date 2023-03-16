package ru.netology.data.enums;

import lombok.Getter;

@Getter
public enum CardDates {
    EXPIRED_DATE(-1, 0),
    NEXT_MONTH(1, 0),
    NEXT_YEAR(0, 1);

    private int month;
    private int year;

    CardDates(int month, int year) {
        this.month = month;
        this.year = year;
    }
}
