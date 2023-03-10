package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardDataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.PaymentPage;
import ru.netology.setup.Setup;

import java.util.Objects;

public class PayByCardTests extends Setup {
    @Test
    void payWithApprovedCardValid() {
        new PaymentPage()
                .clickPaymentButton()
                .fillCard(CardDataHelper.builder().build())
                .clickContinueButton()
                .checkSuccessNotification();
        Assertions.assertEquals(1, Objects.requireNonNull(DbHelper.getPaymentStatuses()).size());
        Assertions.assertEquals("APPROVED", DbHelper.getPaymentStatuses().get(0));
    }

    @Test
        /////Баг, при оплате картой declined, должна всплывать ошибка, что банк отказал проведении операции
    void payWithDeclinedCardValid() {
        new PaymentPage()
                .clickPaymentButton()
                .fillCard(CardDataHelper.builder().build())
                .clickContinueButton()
                .checkErrorNotification();
    }

    @Test
    @DisplayName("Отправка пустой формы при оплате картой")
    void sendingAnEmptyFormWhenPayingByCard() {
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getNumberCardFieldError().getText());
        Assertions.assertEquals("Неверный формат", page.getMonthErrorLabel().getText());
        Assertions.assertEquals("Неверный формат", page.getYearErrorLabel().getText());
        Assertions.assertEquals("Поле обязательно для заполнения", page.getOwnerErrorLabel().getText());
        Assertions.assertEquals("Неверный формат", page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Номер карты' при оплате картой")
    void sendingTheFormWithAnEmptyFieldCardNumberPaymentByCard() {
        var data = CardDataHelper.builder().cardNumber("").build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Месяц' при оплате картой")
    void sendingTheFormWithAnEmptyFieldMonthPaymentByCard() {
        var data = CardDataHelper.builder().cardMonth("").build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Год' при оплате картой")
    void sendingTheFormWithAnEmptyFieldYearPaymentByCard() {
        var data = CardDataHelper.builder().cardYear("").build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Владелец' при оплате картой")
    void sendingTheFormWithAnEmptyFieldOwnerPaymentByCard() {
        var data = CardDataHelper.builder().cardOwner("").build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Поле обязательно для заполнения", page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'CVC' при оплате картой")
    void sendingTheFormWithAnEmptyFieldCvcPaymentByCard() {
        var data = CardDataHelper.builder().cardCvc("").build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("Неверный формат", page.getCvcErrorLabel().getText());
    }
}
