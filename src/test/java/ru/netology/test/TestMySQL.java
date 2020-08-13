package ru.netology.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLUtils;
import ru.netology.page.CardDataEntryPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class TestMySQL {
    DataHelper.CardInfo cardInfo;

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        cardInfo = DataHelper.getCardInfo();
    }

    @AfterEach
    void setAfter() {
        SQLUtils.cleanDB();
    }

    //HAPPY PATH

    //APPROVED card

    @Test
    void shouldBuyTourWithValidDataUseApprovedCard() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterCardData(cardInfo, DataHelper.getApprovedCard());
        CardDataEntryPage.successNotification();
    }

    //DECLINED card
    @Test
    void shouldBuyTourWithValidDataDeclinedCard() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterCardData(cardInfo, DataHelper.getDeclinedCard());
        CardDataEntryPage.errorNotification();
    }

    //APPROVED card
    @Test
    void shouldBuyToCreditTourWithValidDataUseApprovedCard() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterCardData(cardInfo, DataHelper.getApprovedCard());
        CardDataEntryPage.successNotification();
    }

    //DECLINED card
    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCard() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterCardData(cardInfo, DataHelper.getDeclinedCard());
        CardDataEntryPage.errorNotification();
    }


}
