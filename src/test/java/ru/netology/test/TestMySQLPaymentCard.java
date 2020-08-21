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
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        CardDataEntryPage.successNotification();
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
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        CardDataEntryPage.errorNotification();
    }

    @Test
    void shouldBuyTourWithValidDataDeclinedCardCheckBDOrderPaymentId() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        sleep(10000);
        val actual = getOrderPaymentId();
        assertNull(actual);
    }

    @Test
    void shouldBuyTourWithValidDataDeclinedCardCheckBDPaymentTransactionI() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        sleep(10000);
        val transactionId = getPaymentTransactionId();
        assertNotNull(transactionId);
    }

    //SAD PATH

    //CARD NUMBER

    @Test
    void shouldBuyTourWithInvalidCardNumber() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidCardNumber(cardInfo);
    }

    //FIELD MONTH

    @Test
    void shouldBuyTourWithInvalidMonth() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidMonth(cardInfo);
    }

    //FIELD YEAR

    @Test
    void shouldBuyTourWithInvalidPastYear() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidPastYear(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidFutureYear() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidFutureYear(cardInfo);
    }

    //FIELD OWNER

    @Test
    void shouldBuyTourWithInvalidOwnerWithoutSurname() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidCardHolderWithoutSurName(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidRusOwner() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterRusCardHolder(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidNonOwner() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterNonCardHolder(cardInfo);
    }

    //FIELD CVC

    @Test
    void shouldBuyTourWithInvalidNonCVC() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterNonCVC(cardInfo);
    }
}