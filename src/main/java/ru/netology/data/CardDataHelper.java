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
    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    @Builder.Default
    private String number = Cards.APPROVED.getName();
    @Builder.Default
    private String month = generateMonth(1);
    @Builder.Default
    private String year = generateYear(1);
    @Builder.Default
    private String holder = generateName(FAKER);
    @Builder.Default
    private String cvc = FAKER.numerify("###");

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
