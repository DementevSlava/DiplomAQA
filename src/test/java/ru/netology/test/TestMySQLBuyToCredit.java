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
import static ru.netology.data.SQLUtils.getCreditRequestBankId;
import static ru.netology.data.SQLUtils.getOrderPaymentId;

public class TestMySQLBuyToCredit {
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
    void shouldBuyToCreditTourWithValidDataUseApprovedCard() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
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
    void shouldBuyToCreditTourWithValidDataDeclinedCard() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        CardDataEntryPage.errorNotification();
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCardBDOrderPaymentId() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        sleep(10000);
        val actual = getOrderPaymentId();
        assertNull(actual);
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCardCheckBDCreditRequestBankId() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterValidCardData(cardInfo);
        sleep(10000);
        val actual = getCreditRequestBankId();
        assertNotNull(actual);
    }

    //SAD PATH

    //CARD NUMBER

    @Test
    void shouldBuyToCreditTourWithInvalidCardNumber() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterInvalidCardNumber(cardInfo);
    }

    //FIELD MONTH

    @Test
    void shouldBuyToCreditTourWithInvalidMonth() {
        MainPage.openCardPayPage();
        CardDataEntryPage.enterInvalidMonth(cardInfo);
    }

    //FIELD YEAR

    @Test
    void shouldBuyTourWithInvalidPastYear() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterInvalidPastYear(cardInfo);
    }

    @Test
    void shouldBuyToCreditTourWithInvalidFutureYear() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterInvalidFutureYear(cardInfo);
    }

    //FIELD OWNER

    @Test
    void shouldBuyToCreditTourWithInvalidOwnerWithoutSurname() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterInvalidCardHolderWithoutSurName(cardInfo);
    }

    @Test
    void shouldBuyToCreditTourWithInvalidRusOwner() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterRusCardHolder(cardInfo);
    }

    @Test
    void shouldBuyToCreditTourWithInvalidNonOwner() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterNonCardHolder(cardInfo);
    }

    //FIELD CVC

    @Test
    void shouldBuyToCreditTourWithInvalidNonCVC() {
        MainPage.openCreditPayPage();
        CardDataEntryPage.enterNonCVC(cardInfo);
    }
}
