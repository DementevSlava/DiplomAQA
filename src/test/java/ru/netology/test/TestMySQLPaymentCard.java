package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLUtils;
import ru.netology.pages.CardDataEntryPage;
import ru.netology.pages.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.SQLUtils.*;

public class TestMySQLPaymentCard {
    DataHelper.CardInfo cardInfo;
    MainPage mainPage = new MainPage();
    CardDataEntryPage cardDataEntryPage = new CardDataEntryPage();

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        cardInfo = DataHelper.getCardInfo();
    }

    @BeforeAll
    static void startReport() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void setAfter() {
        SQLUtils.cleanDB();
    }

    @AfterAll
    static void CloseReport() {
        SelenideLogger.removeListener("allure");
    }

    //HAPPY PATH

    //APPROVED card

    @Test
    void shouldBuyTourWithValidDataUseApprovedCard() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterApprovedCardData(cardInfo);
        cardDataEntryPage.successNotification();
        val actual = DataHelper.getApprovedCard().getStatus();
        val expected = SQLUtils.getPaymentStatus();
        assertEquals(expected, actual);
        val paymentTransactionId = getPaymentTransactionId();
        assertNotNull(paymentTransactionId);
        val orderPaymentId = getOrderPaymentId();
        assertNotNull(orderPaymentId);
        assertEquals(paymentTransactionId, orderPaymentId);
    }

    //DECLINED card

    @Test
    void shouldBuyTourWithValidDataDeclinedCard() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterDeclinedCardData(cardInfo);
        cardDataEntryPage.errorNotification();
    }

    @Test
    void shouldBuyTourWithValidDataDeclinedCardCheckBDOrderPaymentId() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterDeclinedCardData(cardInfo);
        sleep(10000);
        val actual = getOrderPaymentId();
        assertNull(actual);
    }

    @Test
    void shouldBuyTourWithValidDataDeclinedCardCheckBDPaymentTransactionI() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterDeclinedCardData(cardInfo);
        sleep(10000);
        val transactionId = getPaymentTransactionId();
        assertNotNull(transactionId);
    }

    //SAD PATH

    //CARD NUMBER

    @Test
    void shouldBuyTourWithInvalidCardNumber() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterInvalidCardNumber(cardInfo);
    }

    //FIELD MONTH

    @Test
    void shouldBuyTourWithInvalidMonth() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterInvalidMonth(cardInfo);
    }

    //FIELD YEAR

    @Test
    void shouldBuyTourWithInvalidPastYear() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterInvalidPastYear(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidFutureYear() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterInvalidFutureYear(cardInfo);
    }

    //FIELD OWNER

    @Test
    void shouldBuyTourWithInvalidOwnerWithoutSurname() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterInvalidCardHolderWithoutSurName(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidRusOwner() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterRusCardHolder(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidNonOwner() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterNonCardHolder(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidSymbolOwner() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterSymbolCardHolder(cardInfo);
    }

    //FIELD CVC

    @Test
    void shouldBuyTourWithInvalidNonCVC() {
        mainPage.openCardPayPage();
        cardDataEntryPage.enterNonCVC(cardInfo);
    }
}