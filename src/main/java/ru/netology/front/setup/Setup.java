package ru.netology.front.setup;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.data.DbHelper;

public class Setup {
    @BeforeEach
    void setup() {
        DbHelper.cleanDataBase();
        Selenide.open("http://localhost:8080/");
    }
}
