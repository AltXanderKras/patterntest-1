package ru.netology.pattern.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.pattern.data.DataGenerator;
import ru.netology.pattern.data.DataGenerator.UserInfo;
import ru.netology.pattern.data.DataGenerator.Registration;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selectors.withText;
import com.codeborne.selenide.Condition;

public class AppCardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully plan a meeting")
    void successfulPlanMeeting() {
        UserInfo validUser = Registration.generateUser("ru");
        int addDaysForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(addDaysForFirstMeeting);
        int addDaysForSecondMeeting = 8;
        String secondMeetingDate = DataGenerator.generateDate(addDaysForSecondMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $(".calendar-input input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate));
        $(".calendar-input input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $(".calendar-input input").setValue(secondMeetingDate);
        $(".button").click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $("[data-test-id=replan-notification]")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(Condition.visible, Duration.ofSeconds(20));
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }
}





