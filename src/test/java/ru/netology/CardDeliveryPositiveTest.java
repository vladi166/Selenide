package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryPositiveTest {
    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void MustRegister() { // Успешная регистрация с валидными данными
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(exactText("Встреча успешно забронирована на " + planningDate));


    }

    @Test
    void HyphenInTheNameField() { // дефис в поле "Фамилия и Имя"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков-Капустин Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldBe(exactText("Успешно!"));
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(exactText("Встреча успешно забронирована на " + planningDate));
    }
}
