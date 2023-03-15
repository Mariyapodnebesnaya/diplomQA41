package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.api.Api;
import ru.netology.data.CardDataHelper;
import ru.netology.data.DbHelper;
import ru.netology.data.enums.Cards;

public class APITests {
    @Test
    void testPayCardApproved() {
        DbHelper.getPaymentStatuses();
        Assertions.assertEquals("APPROVED", Api.postPay(CardDataHelper.builder().build()).getStatus());
    }

    @Test
    void testPayCardDeclined() {
        DbHelper.getPaymentStatuses();
        Assertions.assertEquals("DECLINED", Api.postPay(CardDataHelper.builder()
                .number(Cards.DECLINED.getName())
                .build()).getStatus());
    }

    @Test
    void testPayCardNotExistInDb() {
        Api.postPayAsError(CardDataHelper.builder()
                .number(Cards.NOT_EXIST_IN_DB.getName())
                .build());
    }

    @Test
    void testCreditApproved() {
        DbHelper.getCreditStatuses();
        Assertions.assertEquals("APPROVED", Api.postCredit(CardDataHelper.builder().build()).getStatus());
    }

    @Test
    void testCreditDeclined() {
        DbHelper.getCreditStatuses();
        Assertions.assertEquals("DECLINED", Api.postCredit(CardDataHelper.builder()
                .number(Cards.DECLINED.getName())
                .build()).getStatus());
    }

    @Test
    void testPayCreditNotExistInDb() {
        Api.postCreditAsError(CardDataHelper.builder()
                .number(Cards.NOT_EXIST_IN_DB.getName())
                .build());
    }
}
