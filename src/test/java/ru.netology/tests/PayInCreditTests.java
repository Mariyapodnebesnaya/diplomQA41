package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardDataHelper;
import ru.netology.data.enums.Cards;
import ru.netology.front.page.PaymentPage;
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
                .fillCard(CardDataHelper.builder().number(Cards.DECLINED.getName()).build())
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

    @Test
    @DisplayName("Отправка формы с пустым полем 'Номер карты' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldCardNumberCreditByCard() {
        var data = CardDataHelper.builder().number("").build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Месяц' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldMonthCreditByCard() {
        var data = CardDataHelper.builder().month("").build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Год' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldYearCreditByCard() {
        var data = CardDataHelper.builder().year("").build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Владелец' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldOwnerCreditByCard() {
        var data = CardDataHelper.builder().holder("").build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Поле обязательно для заполнения", page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'CVC' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldCvcCreditByCard() {
        var data = CardDataHelper.builder().cvc("").build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getCvcErrorLabel().getText());
    }
}
