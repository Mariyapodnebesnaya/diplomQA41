package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardDataHelper;
import ru.netology.data.enums.Cards;
import ru.netology.page.PaymentPage;
import ru.netology.setup.Setup;

public class PayInCreditTests extends Setup {
    @Test
    void payOnCreditWithApprovedCardValid() {
        new PaymentPage()
                .clickPaymentCreditButton()
                .fillCard(CardDataHelper.builder().build())
                .clickContinueButton()
                .checkSuccessNotification();
    }

    @Test
        /////Баг, при оплате в кредит по данным карты  declined, должна всплывать ошибка, что банк отказал проведении операции
    void payOnCreditWithDeclinedCardValid() {
        new PaymentPage()
                .clickPaymentCreditButton()
                .fillCard(CardDataHelper.builder().cardNumber(Cards.DECLINED.getName()).build())
                .clickContinueButton()
                .checkErrorNotification();
    }

    @Test
    @DisplayName("Отправка пустой формы при оплате в кредит")
    void sendingAnEmptyFormWhenPayInCredit() {
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getNumberCardFieldError().getText());
        Assertions.assertEquals("Неверный формат", page.getMonthErrorLabel().getText());
        Assertions.assertEquals("Неверный формат", page.getYearErrorLabel().getText());
        Assertions.assertEquals("Поле обязательно для заполнения", page.getOwnerErrorLabel().getText());
        Assertions.assertEquals("Неверный формат", page.getCvcErrorLabel().getText());
    }
}
