package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class NegativeTests {
    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void EmptyCityField() { //пустое поле "город"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void CityFieldInLatin() { //поле "город" на латинице
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Detroit");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void CityIsNotAnAdministrativeCenter() { //город не административный центр
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Асбест");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
      void EmptyDateField() { //поле "Дата" пустое
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue("");
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void EmptyNameField() { //Пустое поле "Фамилия и Имя"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void NameFieldInLatin() { //поле "Фамилия и Имя" на латинице
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Pirozhkov Artur");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void NumbersInTheNameField() { //цифры в поле "Фамилия и Имя"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("65856395269");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void SpecialCharactersInTheNameField() { //спецсимволы в поле "Фамилия и Имя"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("@#$%^&*");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void LettersAndNumbersInTheNameField() { //цифры и буквы в поле "Фамилия и Имя"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("3.14р0жк0в Ар7ур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void LettersAndSpecialCharactersInTheNameField() { //спецсимволы и буквы в поле "Фамилия и Имя"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("№ир@жк@в !Артур?");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void EmptyPhoneField() { //пустое поле "телефон"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void WithoutThePlusSignPhoneField() { //без знака "+" в поле "телефон"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("89999999994");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void Enter10DigitsInThePhoneField() { //10 цифр в поле "телефон"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+7999123456");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void Enter12DigitsInThePhoneField() { //12 цифр в поле "телефон"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+799999999945");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void LettersInPhoneField() { //буквы в поле "телефон"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("буквы");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void SpecialCharactersInPhoneField() { //спецсимволы в поле "телефон"
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("!@$^%&*");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void EmptyCheckbox() { //пустой чекбокс
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=city] [placeholder='Город']").setValue("Екатеринбург");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Пирожков Артур");
        $("[data-test-id='phone'] input").setValue("+79999999994");
        $(".button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldBe(visible)
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
