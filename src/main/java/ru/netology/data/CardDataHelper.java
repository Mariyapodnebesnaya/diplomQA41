package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;
import ru.netology.data.enums.Cards;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Builder
public class CardDataHelper {
    private static final Faker fakerEn = new Faker(new Locale("en"));
    private static final Faker fakerRu = new Faker(new Locale("ru"));

    @Builder.Default
    private String cardNumber = Cards.APPROVED.getName();
    @Builder.Default
    private String cardMonth = generateMonth(1);
    @Builder.Default
    private String cardYear = generateYear(0);
    @Builder.Default
    private String cardOwner = generateName(fakerEn);
    @Builder.Default
    private String cardCvc = fakerEn.numerify("###");

    private static String generateMonth(int plusMonth) {
        return LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String generateYear(int plusYear) {
        return LocalDate.now().plusYears(plusYear).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String generateName(Faker faker) {
        return faker.name().lastName() + " " + faker.name().firstName();
    }
}
