package ru.netology.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.data.DbHelper;

public class BaseFrontTest {
    @BeforeEach
    void setup() {
        DbHelper.cleanDataBase();
        Selenide.open("http://localhost:8080/");
    }
}
