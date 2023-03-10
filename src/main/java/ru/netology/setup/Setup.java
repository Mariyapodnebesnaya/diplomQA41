package ru.netology.setup;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.data.DbHelper;

public class Setup {
    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:8080/");
    }

    @AfterEach
    void cleanDataBase() {
        DbHelper.cleanDataBase();
    }
}
