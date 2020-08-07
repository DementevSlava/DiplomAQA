package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLUtils;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentByCardPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestMySQL {
    DataHelper.CardInfo cardInfo;

    MainPage mainPage = new MainPage();

    //private SelenideElement button = $$("span.button__text").find(exactText("Продолжить"));
    // private SelenideElement successNotification = $(withText("Операция одобрена Банком."));

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        cardInfo = DataHelper.getCardInfo();
    }

    @AfterEach
    void setAfter(){
        SQLUtils.cleanDB();
    }

    @Test
    void BuyTourWithValidDataApprovedCard() {

        PaymentByCardPage paymentByCardPage = new PaymentByCardPage();
        mainPage.openCardPayPage();
        paymentByCardPage.enterCardData(cardInfo);
        SelenideElement button = $$("span.button__text").find(exactText("Продолжить"));
        button.click();
        SelenideElement successNotification = $(withText("Операция одобрена Банком."));
        successNotification.waitUntil(Condition.visible, 30000);
    }
}
