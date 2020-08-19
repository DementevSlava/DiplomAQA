package ru.netology.test;


import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLUtils;
import ru.netology.page.CardDataEntryPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.SQLUtils.*;

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
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getApprovedCard());
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

    @Test
    void shouldBuyToCreditTourWithValidDataUseApprovedCard() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getApprovedCard());
        CardDataEntryPage.successNotification();
        val actual = DataHelper.getApprovedCard().getStatus();
        val expected = SQLUtils.getCreditRequestStatus();
        assertEquals(expected, actual);
        val creditRequestBankId = getCreditRequestBankId();
        assertNotNull(creditRequestBankId);
        val orderPaymentId = getOrderPaymentId();
        assertNotNull(orderPaymentId);
        assertEquals(creditRequestBankId, orderPaymentId);
    }

    //DECLINED card

    @Test
    void shouldBuyTourWithValidDataDeclinedCard() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getDeclinedCard());
        CardDataEntryPage.errorNotification();
    }

    @Test
    void shouldBuyTourWithValidDataDeclinedCardCheckBDOrderPaymentId() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getDeclinedCard());
        sleep(10000);
        val actual = getOrderPaymentId();
        assertNull(actual);
    }

    @Test
    void shouldBuyTourWithValidDataDeclinedCardCheckBDPaymentTransactionI() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getDeclinedCard());
        sleep(10000);
        val transactionId = getPaymentTransactionId();
        assertNotNull(transactionId);
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCard() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getDeclinedCard());
        CardDataEntryPage.errorNotification();
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCardBDOrderPaymentId() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getDeclinedCard());
        sleep(10000);
        val actual = getOrderPaymentId();
        assertNull(actual);
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCardCheckBDCreditRequestBankId() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo, DataHelper.getDeclinedCard());
        sleep(10000);
        val actual = getCreditRequestBankId();
        assertNotNull(actual);
    }

    //SAD PATH

    @Test
    void shouldBuyTourWithInvalidCardNumber(){
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidCardNumber(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidMonth(){
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidMonth(cardInfo);
    }
}