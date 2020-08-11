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

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        cardInfo = DataHelper.getCardInfo();
    }

    @AfterEach
    void setAfter(){
        SQLUtils.cleanDB();
    }

    //HAPPY PATH

    //APPROVED card

    @Test
    void shouldBuyTourWithValidDataUseApprovedCard() {
        MainPage.openCardPayPage();
        PaymentByCardPage paymentByCardPage = new PaymentByCardPage();
        paymentByCardPage.enterValidDataApprovedCard(cardInfo);
        SelenideElement successNotification = $(withText("Операция одобрена Банком."));
        successNotification.waitUntil(Condition.visible, 30000);
    }



    //DECLINED card
    @Test
    void shouldBuyTourWithValidDataDeclinedCard() {
        MainPage.openCreditPayPage();
        PaymentByCardPage paymentByCardPage = new PaymentByCardPage();
        paymentByCardPage.enterValidDataDeclinedCard(cardInfo);
        SelenideElement successNotification = $(withText("Операция одобрена Банком."));
        successNotification.waitUntil(Condition.visible, 30000);
    }


}
