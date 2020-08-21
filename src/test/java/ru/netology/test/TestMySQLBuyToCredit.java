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
    void shouldBuyToCreditTourWithValidDataUseApprovedCard() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterApprovedCardData(cardInfo);
        cardDataEntryPage.successNotification();
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
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterDeclinedCardData(cardInfo);
        cardDataEntryPage.errorNotification();
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCardBDOrderPaymentId() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterDeclinedCardData(cardInfo);
        sleep(10000);
        val actual = getOrderPaymentId();
        assertNull(actual);
    }

    @Test
    void shouldBuyToCreditTourWithValidDataDeclinedCardCheckBDCreditRequestBankId() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterDeclinedCardData(cardInfo);
        sleep(10000);
        val actual = getCreditRequestBankId();
        assertNotNull(actual);
    }

    //SAD PATH

    //CARD NUMBER

    @Test
    void shouldBuyToCreditTourWithInvalidCardNumber() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterInvalidCardNumber(cardInfo);
    }

    //FIELD MONTH

    @Test
    void shouldBuyToCreditTourWithInvalidMonth() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterInvalidMonth(cardInfo);
    }

    //FIELD YEAR

    @Test
    void shouldBuyTourWithInvalidPastYear() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterInvalidPastYear(cardInfo);
    }

    @Test
    void shouldBuyToCreditTourWithInvalidFutureYear() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterInvalidFutureYear(cardInfo);
    }

    //FIELD OWNER

    @Test
    void shouldBuyToCreditTourWithInvalidOwnerWithoutSurname() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterInvalidCardHolderWithoutSurName(cardInfo);
    }

    @Test
    void shouldBuyToCreditTourWithInvalidRusOwner() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterRusCardHolder(cardInfo);
    }

    @Test
    void shouldBuyToCreditTourWithInvalidNonOwner() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterNonCardHolder(cardInfo);
    }

    @Test
    void shouldBuyTourWithInvalidSymbolOwner() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterSymbolCardHolder(cardInfo);
    }

    //FIELD CVC

    @Test
    void shouldBuyToCreditTourWithInvalidNonCVC() {
        mainPage.openCreditPayPage();
        cardDataEntryPage.enterNonCVC(cardInfo);
    }
}
