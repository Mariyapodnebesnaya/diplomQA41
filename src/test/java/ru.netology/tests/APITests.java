package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.api.Api;
import ru.netology.data.CardDataHelper;
import ru.netology.data.DbHelper;
import ru.netology.data.enums.Cards;

public class APITests {
    @Test
    void test() {
        Assertions.assertEquals("APPROVED", Api.postPay(CardDataHelper.builder()
                        .build())
                .getStatus());
    }

    @Test
    void test2() {
        DbHelper.getPaymentStatuses();
        Assertions.assertEquals("DECLINED", Api.postPay(CardDataHelper.builder()
                .number(Cards.DECLINED.getName())
                .build()).getStatus());
    }

    @Test
    void testCredit() {
        Assertions.assertEquals("APPROVED", Api.postCredit(CardDataHelper.builder().build()).getStatus());
    }

}
