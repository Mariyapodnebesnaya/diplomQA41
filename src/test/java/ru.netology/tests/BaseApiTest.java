package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import ru.netology.data.DbHelper;

public class BaseApiTest {
    @BeforeEach
    void setup() {
        DbHelper.cleanDataBase();
    }
}
