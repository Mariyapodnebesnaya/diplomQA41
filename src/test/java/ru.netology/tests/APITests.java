package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.api.Api;
import ru.netology.data.CardDataHelper;
import ru.netology.data.DbHelper;
import ru.netology.data.enums.Cards;

public class APITests extends BaseApiTest {
    public static final String APPROVED = "APPROVED";
    public static final String DECLINED = "DECLINED";

    @Test
    void testPayCardApproved() {
        Assertions.assertEquals(APPROVED, Api.postPay(CardDataHelper.builder().build()).getStatus());
        Assertions.assertEquals(APPROVED, DbHelper.getPaymentStatus());
    }

    @Test
    void testPayCardDeclined() {
        Assertions.assertEquals(DECLINED, Api.postPay(CardDataHelper.builder()
                .number(Cards.DECLINED.getName())
                .build()).getStatus());
        Assertions.assertEquals(DECLINED, DbHelper.getPaymentStatus());
    }

    @Test
    void testPayCardNotExistInDb() {
        Api.postPayAsError(CardDataHelper.builder()
                .number(Cards.NOT_EXIST_IN_DB.getName())
                .build());
    }

    @Test
    void testCreditApproved() {
        Assertions.assertEquals(APPROVED, Api.postCredit(CardDataHelper.builder().build()).getStatus());
        Assertions.assertEquals(APPROVED, DbHelper.getCreditStatus());
    }

    @Test
    void testCreditDeclined() {
        Assertions.assertEquals(DECLINED, Api.postCredit(CardDataHelper.builder()
                .number(Cards.DECLINED.getName())
                .build()).getStatus());
        Assertions.assertEquals(DECLINED, DbHelper.getCreditStatus());
    }

    @Test
    void testPayCreditNotExistInDb() {
        Api.postCreditAsError(CardDataHelper.builder()
                .number(Cards.NOT_EXIST_IN_DB.getName())
                .build());
    }
}
