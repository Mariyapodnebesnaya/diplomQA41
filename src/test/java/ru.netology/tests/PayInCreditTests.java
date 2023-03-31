package ru.netology.tests;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.CardDataHelper;
import ru.netology.data.DbHelper;
import ru.netology.data.enums.Cards;
import ru.netology.data.enums.ErrorMessages;
import ru.netology.front.page.PaymentPage;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PayInCreditTests extends BaseFrontTest {
    @Test
    @DisplayName("Отправка формы заполненная валидными данными при оплате в кредит картой Approved")
    void payOnCreditWithApprovedCardValid() {
        new PaymentPage()
                .clickPaymentCreditButton()
                .fillCard(CardDataHelper.builder().build())
                .clickContinueButton()
                .checkSuccessNotification();
        Assertions.assertNotNull(DbHelper.getCreditStatus());
        Assertions.assertEquals("APPROVED", DbHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Отправка формы заполненная валидными данными при оплате в кредит картой Declined")
        /////Баг, при оплате в кредит по данным карты  declined, должна всплывать ошибка, что банк отказал проведении операции
    void payOnCreditWithDeclinedCardValid() {
        new PaymentPage()
                .clickPaymentCreditButton()
                .fillCard(CardDataHelper.builder()
                        .number(Cards.DECLINED.getName())
                        .build())
                .clickContinueButton()
                .checkErrorNotification();
        Assertions.assertNotNull(DbHelper.getCreditStatus());
        Assertions.assertEquals("DECLINED", DbHelper.getCreditStatus());
    }

    @Test
    @DisplayName("Отправка пустой формы при оплате в кредит")
    void sendingAnEmptyFormWhenPayInCredit() {
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
        Assertions.assertEquals(ErrorMessages.REQUIRED_FIELD.getText(), page.getOwnerErrorLabel().getText());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Номер карты' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldCardNumberCreditByCard() {
        var data = CardDataHelper.builder()
                .number("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Месяц' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldMonthCreditByCard() {
        var data = CardDataHelper.builder()
                .month("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Год' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldYearCreditByCard() {
        var data = CardDataHelper.builder()
                .year("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Владелец' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldOwnerCreditByCard() {
        var data = CardDataHelper.builder()
                .holder("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.REQUIRED_FIELD.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'CVC' при оплате в кредит")
    void sendingTheFormWithAnEmptyFieldCvcCreditByCard() {
        var data = CardDataHelper.builder()
                .cvc("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' длина значения менее 16 цифр при оплате в кредит")
    void theCardNumberFieldIsLessThan16DigitsLongWhenPayByCard() {
        var data = CardDataHelper.builder()
                .number("4444 4444 4444 444")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' Введение номера карты не существующего в БД при оплате в кредит")
    void theCardNumberFieldEnteringCardNumberThatDoesNoExistInTheDatabasePayByCard() {
        var data = CardDataHelper.builder()
                .number(Cards.NOT_EXIST_IN_DB.getName())
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton()
                .checkErrorNotification();
    }

    @Test
    @DisplayName("Поле 'Номер карты' Введение номера карты 17 цифр при оплате в кредит")
    void ardNumberFieldEnteringTheCardNumber17DigitsWhenPayingByCard() {
        var data = CardDataHelper.builder()
                .number(RandomStringUtils.randomNumeric(17))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertNotNull(page.getNumberCardInput().getValue());
        Assertions.assertTrue(page.getNumberCardInput().getValue().matches("((\\d{4}[\\s-]?){3}\\d{4})"));
    }

    @Test
    @DisplayName("Поле 'Номер карты' ввод значения на кириллице при оплате в кредит")
    void theCardNumberFieldInputValueInCyrillicPayByCard() {
        var data = CardDataHelper.builder()
                .number("плралпвлы")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getNumberCardInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' ввод значения на латинице при оплате в кредит")
    void theCardNumberFieldInputValueInLatinPayByCard() {
        var data = CardDataHelper.builder()
                .number("dlfgjoh")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getNumberCardInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' ввод спецсимволов при оплате в кредит")
    void theCardNumberFieldInputEnteringSpecialCharactersPayByCard() {
        var data = CardDataHelper.builder()
                .number("+%&$*")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getNumberCardInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод значения месяца из одной цифры при оплате в кредит")
    void fieldMonthEnterTheValueOfTheMonthFromOneDigitWhenPayByCard() {
        var data = CardDataHelper.builder()
                .month("2")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод значения месяца более 12 при оплате в кредит")
    void fieldMonthEnterTheValueOfTheMonthFromOver12WhenPayByCard() {
        var data = CardDataHelper.builder()
                .month("13")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.INVALID_CARD_EXPIRATION_DATE.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод значения месяца 00 при оплате в кредит")
    void fieldMonthEnterTheValueOfTheMonthFrom00WhenPayByCard() {
        var data = CardDataHelper.builder()
                .month("00")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.INVALID_CARD_EXPIRATION_DATE.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод буквенного значения при оплате в кредит")
    void fieldMonthEnterTheEnteringLiteralValueWhenPayByCard() {
        var data = CardDataHelper.builder()
                .month(RandomStringUtils.randomAlphabetic(2))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getMonthInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения года из одной цифры при оплате в кредит")
    void fieldMonthEnterTheValueOfTheYearFromOneDigitWhenPayByCard() {
        var data = CardDataHelper.builder()
                .year("2")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения года 00 при оплате в кредит")
    void fieldMonthEnterTheValueLessThanCurrentYearWhenPayByCard() {
        var data = CardDataHelper.builder()
                .year("22")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.CARD_EXPIRED.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения больше на +6 лет текущего года при оплате в кредит")
    void fieldMonthEnterTheValueMoreBy6YearsOfTheCurrentYearWhenPayByCard() {
        var year = Year.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
        var data = CardDataHelper.builder()
                .year(year)
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.INVALID_CARD_EXPIRATION_DATE.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения меньше текущего года при оплате в кредит")
    void fieldMonthEnterTheValueOfTheYearFrom00WhenPayByCard() {
        var data = CardDataHelper.builder()
                .year("00")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.CARD_EXPIRED.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод буквенного значения при оплате в кредит")
    void fieldYearEnterTheEnteringLiteralValueWhenPayByCard() {
        var data = CardDataHelper.builder()
                .year(RandomStringUtils.randomAlphabetic(2))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getYearInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Владелец' ввод значения на кириллице при оплате в кредит")
    void fieldOwnerEnterTheValueInCyrillicWhenPayByCard() {
        var faker = new Faker(new Locale("ru"));
        var holder = faker.name().lastName() + " " + faker.name().firstName();
        var data = CardDataHelper.builder()
                .holder(holder)
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Владелец' ввод цифр при оплате в кредит")
    void fieldOwnerEnterTheNumbersWhenPayByCard() {
        var data = CardDataHelper.builder()
                .holder("35")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Владелец' ввод спецсимволов при оплате в кредит")
    void fieldOwnerEnterTheSpecialCharactersWhenPayByCard() {
        var data = CardDataHelper.builder()
                .holder("$%#+/")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'CVC' ввод одной цифры при оплате в кредит")
    void fieldCVCEnterOneDigitWhenPayByCard() {
        var data = CardDataHelper.builder()
                .cvc("2")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'CVC' ввод буквенного значения при оплате в кредит")
    void fieldCVCEnterTheEnteringLiteralValueWhenPayByCard() {
        var data = CardDataHelper.builder()
                .cvc(RandomStringUtils.randomAlphabetic(3))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getCvcInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'CVC' ввод спецсимволов при оплате в кредит")
    void fieldCVCEnterTheSpecialCharactersPayByCard() {
        var data = CardDataHelper.builder()
                .cvc("№/*")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentCreditButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }
}
