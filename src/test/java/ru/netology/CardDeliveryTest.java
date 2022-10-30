package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

//    Код для генерации даты можно вынести в отдельный метод, передавать в него количество дней,
//    а возвращать отформатированную строку с датой. Метод может выглядеть примерно так:
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

//    указанный метод принимает количество дней сдвига относительно текущей даты,
//    а возвращает отформатированную строку с датой. Вы получите возможность генерировать различные даты, как валидные, так и невалидные.
//    Сохранить полученную строку можно в локальной переменной тестового метода
    String planningDate = generateDate(3);

//    Данный метод принимает количество дней сдвига относительно текущей даты,
//    а возвращает отформатированную строку с датой, только на валидные значения.
//    LocalDate localDate = LocalDate.now().plusDays(3);
//    DateTimeFormatter data = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//    String generateDate = localDate.format(data);

    @Test
    public void test() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[placeholder='Город']").setValue("Москва");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(planningDate);
        $("[name='name']").setValue("Иванов Иван");
        $("[name='phone']").setValue("+71231231233");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(withText("Забронировать")).click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + (planningDate)), Duration.ofSeconds(15));
    }
}
