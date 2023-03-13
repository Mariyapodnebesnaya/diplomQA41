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
import ru.netology.setup.Setup;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
        Assertions.assertEquals(ErrorMessages.REQUIRED_FIELD.getText(), page.getOwnerErrorLabel().getText());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Номер карты' при оплате картой")
    void sendingTheFormWithAnEmptyFieldCardNumberPaymentByCard() {
        var data = CardDataHelper.builder()
                .number("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Месяц' при оплате картой")
    void sendingTheFormWithAnEmptyFieldMonthPaymentByCard() {
        var data = CardDataHelper.builder()
                .month("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Год' при оплате картой")
    void sendingTheFormWithAnEmptyFieldYearPaymentByCard() {
        var data = CardDataHelper.builder()
                .year("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'Владелец' при оплате картой")
    void sendingTheFormWithAnEmptyFieldOwnerPaymentByCard() {
        var data = CardDataHelper.builder()
                .holder("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.REQUIRED_FIELD.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Отправка формы с пустым полем 'CVC' при оплате картой")
    void sendingTheFormWithAnEmptyFieldCvcPaymentByCard() {
        var data = CardDataHelper.builder()
                .cvc("")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' длина значения менее 16 цифр при оплате картой")
    void theCardNumberFieldIsLessThan16DigitsLongWhenPayByCard() {
        var data = CardDataHelper.builder()
                .number("4444 4444 4444 444")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' Введение номера карты не существующего в БД при оплате картой")
    void theCardNumberFieldEnteringCardNumberThatDoesNoExistInTheDatabasePayByCard() {
        var data = CardDataHelper.builder()
                .number(Cards.NOT_EXIST_IN_DB.getName())
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton()
                .checkErrorNotification();
    }

    @Test
    @DisplayName("Поле 'Номер карты' Введение номера карты 17 цифр при оплате картой")
    void ardNumberFieldEnteringTheCardNumber17DigitsWhenPayingByCard() {
        var data = CardDataHelper.builder()
                .number(RandomStringUtils.randomNumeric(17))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertNotNull(page.getNumberCardInput().getValue());
        Assertions.assertTrue(page.getNumberCardInput().getValue().matches("\\d{4} \\d{4} \\d{4} \\d{4}"));
    }

    @Test
    @DisplayName("Поле 'Номер карты' ввод значения на кириллице при оплате картой")
    void theCardNumberFieldInputValueInCyrillicPayByCard() {
        var data = CardDataHelper.builder()
                .number("плралпвлы")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getNumberCardInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' ввод значения на латинице при оплате картой")
    void theCardNumberFieldInputValueInLatinPayByCard() {
        var data = CardDataHelper.builder()
                .number("dlfgjoh")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getNumberCardInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Номер карты' ввод спецсимволов при оплате картой")
    void theCardNumberFieldInputEnteringSpecialCharactersPayByCard() {
        var data = CardDataHelper.builder()
                .number("+%&$*")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getNumberCardInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getNumberCardFieldError().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод значения месяца из одной цифры при оплате картой")
    void fieldMonthEnterTheValueOfTheMonthFromOneDigitWhenPayByCard() {
        var data = CardDataHelper.builder()
                .month("2")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод значения месяца более 12 при оплате картой")
    void fieldMonthEnterTheValueOfTheMonthFromOver12WhenPayByCard() {
        var data = CardDataHelper.builder()
                .month("13")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.INVALID_CARD_EXPIRATION_DATE.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    // Баг при вводе 00  в поле месяц, форма отправляется, всплывает сообщение : Успешно. Операция одобрена банком. ОР - при отправке формы должна всплывать ошибка под полем месяц Неверно указан срок действия карты
    @DisplayName("Поле 'Месяц' ввод значения месяца 00 при оплате картой")
    void fieldMonthEnterTheValueOfTheMonthFrom00WhenPayByCard() {
        var data = CardDataHelper.builder()
                .month("00")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.INVALID_CARD_EXPIRATION_DATE.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Месяц' ввод буквенного значения при оплате картой")
    void fieldMonthEnterTheEnteringLiteralValueWhenPayByCard() {
        var data = CardDataHelper.builder()
                .month(RandomStringUtils.randomAlphabetic(2))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getMonthInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getMonthErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения года из одной цифры при оплате картой")
    void fieldMonthEnterTheValueOfTheYearFromOneDigitWhenPayByCard() {
        var data = CardDataHelper.builder()
                .year("2")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения года 00 при оплате картой")
    void fieldMonthEnterTheValueLessThanCurrentYearWhenPayByCard() {
        var data = CardDataHelper.builder()
                .year("22")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.CARD_EXPIRED.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения больше на +6 лет текущего года при оплате картой")
    void fieldMonthEnterTheValueMoreBy6YearsOfTheCurrentYearWhenPayByCard() {
        var year = Year.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
        var data = CardDataHelper.builder()
                .year(year)
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.CARD_EXPIRED.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод значения меньше текущего года при оплате картой")
    void fieldMonthEnterTheValueOfTheYearFrom00WhenPayByCard() {
        var data = CardDataHelper.builder()
                .year("00")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.CARD_EXPIRED.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Год' ввод буквенного значения при оплате картой")
    void fieldYearEnterTheEnteringLiteralValueWhenPayByCard() {
        var data = CardDataHelper.builder()
                .year(RandomStringUtils.randomAlphabetic(2))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getYearInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getYearErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Владелец' ввод значения на кириллице при оплате картой")
    void fieldOwnerEnterTheValueInCyrillicWhenPayByCard() {
        var faker = new Faker(new Locale("ru"));
        var holder = faker.name().lastName() + " " + faker.name().firstName();
        var data = CardDataHelper.builder()
                .holder(holder)
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Владелец' ввод цифр при оплате картой")
    void fieldOwnerEnterTheNumbersWhenPayByCard() {
        var data = CardDataHelper.builder()
                .holder("35")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'Владелец' ввод спецсимволов при оплате картой")
    void fieldOwnerEnterTheSpecialCharactersWhenPayByCard() {
        var data = CardDataHelper.builder()
                .holder("$%#+/")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getOwnerErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'CVC' ввод одной цифры при оплате картой")
    void fieldCVCEnterOneDigitWhenPayByCard() {
        var data = CardDataHelper.builder()
                .cvc("2")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'CVC' ввод буквенного значения при оплате картой")
    void fieldCVCEnterTheEnteringLiteralValueWhenPayByCard() {
        var data = CardDataHelper.builder()
                .cvc(RandomStringUtils.randomAlphabetic(3))
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals("", page.getCvcInput().getValue());
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }

    @Test
    @DisplayName("Поле 'CVC' ввод спецсимволов при оплате картой")
    void fieldCVCEnterTheSpecialCharactersPayByCard() {
        var data = CardDataHelper.builder()
                .cvc("№/*")
                .build();
        var page = new PaymentPage();
        page
                .clickPaymentButton()
                .fillCard(data)
                .clickContinueButton();
        Assertions.assertEquals(ErrorMessages.WRONG_FORMAT.getText(), page.getCvcErrorLabel().getText());
    }
}
