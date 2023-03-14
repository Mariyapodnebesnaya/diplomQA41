package ru.netology.front.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import ru.netology.data.CardDataHelper;

import java.time.Duration;

@Getter
public class PaymentPage {
    private SelenideElement purchasePageTitle = Selenide.$(Selectors.byText("Путешествие дня"));
    private SelenideElement paymentButton = Selenide.$x("//span[text()='Купить']//ancestor-or-self::button");
    private SelenideElement creditButton = Selenide.$x("//span[text()='Купить в кредит']//ancestor-or-self::button");

    private SelenideElement header = Selenide.$x("//form/preceding-sibling::h3");

    private SelenideElement numberCardInput = Selenide.$x("//*[text()='Номер карты']/following-sibling::*/input[@class='input__control']");
    private SelenideElement numberCardFieldError = Selenide.$x("//span[@class='input__inner' and contains(.,'Номер карты')]//span[@class='input__sub']");  /// неверный формат

    private SelenideElement monthInput = Selenide.$x("//span[@class='input__inner' and contains(.,'Месяц')]//input");
    private SelenideElement monthErrorLabel = Selenide.$x("//span[@class='input__inner' and contains(.,'Месяц')]//span[@class='input__sub']");

    private SelenideElement yearInput = Selenide.$x("//*[text()='Год']/following-sibling::*/input[@class='input__control']");
    private SelenideElement yearErrorLabel = Selenide.$x("//span[@class='input__inner' and contains(.,'Год')]//span[@class='input__sub']"); /// Нверный формат

    private SelenideElement ownerField = Selenide.$x("//*[text()='Владелец']//following-sibling::*/input[@class= 'input__control']");
    private SelenideElement ownerErrorLabel = Selenide.$x("//span[@class='input__inner' and contains(.,'Владелец')]//span[@class='input__sub']"); /// Поле обязательно для заполнения

    private SelenideElement cvcInput = Selenide.$x("//*[text()='CVC/CVV']//following-sibling::*/input[@class= 'input__control']");
    private SelenideElement cvcErrorLabel = Selenide.$x("//span[@class='input__inner' and contains(.,'CVC/CVV')]//span[@class='input__sub']");//// Неверный формат


    private SelenideElement continueButton = Selenide.$x("//span[text()='Продолжить']");
    private SelenideElement notificationSuccessTitle = Selenide.$x("//div[contains(@class, 'notification_status_ok')]//div[@class='notification__title']");
    private SelenideElement notificationSuccessMessage = Selenide.$x("//div[contains(@class, 'notification_status_ok')]//div[@class='notification__content']");

    private SelenideElement notificationErrorTitle = Selenide.$x("//div[contains(@class, 'notification_status_error')]//div[@class='notification__title']");
    private SelenideElement notificationErrorMessage = Selenide.$x("//div[contains(@class, 'notification_status_error')]//div[@class='notification__content']");

    public PaymentPage() {
        purchasePageTitle.shouldBe(Condition.visible);
        paymentButton.shouldBe(Condition.visible);
        creditButton.shouldBe(Condition.visible);
    }

    public PaymentPage clickPaymentButton() {
        paymentButton.click();
        header.shouldBe(Condition.text("Оплата по карте"));
        return this;
    }

    public PaymentPage clickPaymentCreditButton() {
        creditButton.click();
        header.shouldBe(Condition.text("Кредит по данным карты"));
        return this;
    }

    public PaymentPage fillCard(CardDataHelper card) {
        numberCardInput.setValue(card.getNumber());
        monthInput.setValue(card.getMonth());
        yearInput.setValue(card.getYear());
        ownerField.setValue(card.getHolder());
        cvcInput.setValue(card.getCvc());
        return this;
    }

    public PaymentPage clickContinueButton() {
        continueButton.click();
        return this;
    }

    public PaymentPage checkSuccessNotification() {
        notificationSuccessTitle.shouldBe(Condition.visible, Duration.ofSeconds(20));
        Assertions.assertEquals("Успешно", notificationSuccessTitle.getText());
        Assertions.assertEquals("Операция одобрена Банком.", notificationSuccessMessage.getText());
        return this;
    }

    public PaymentPage checkErrorNotification() {
        notificationErrorTitle.shouldBe(Condition.visible, Duration.ofSeconds(20));
        Assertions.assertEquals("Ошибка", notificationErrorTitle.getText());
        Assertions.assertEquals("Ошибка! Банк отказал в проведении операции.", notificationErrorMessage.getText());
        return this;
    }
}
