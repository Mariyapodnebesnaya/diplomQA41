package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.api.Api;
import ru.netology.data.CardDataHelper;

public class APITests {
    @Test
    void test() {
        Assertions.assertEquals("APPROVED", Api.postPay(CardDataHelper.builder().build()).getStatus());
    }
}
