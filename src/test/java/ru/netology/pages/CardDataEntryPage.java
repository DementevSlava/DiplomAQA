package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDataEntryPage {


    private static SelenideElement heading = $$("h3[class]").find(exactText("Оплата по карте"));
    private static SelenideElement heading2 = $$("h3[class]").find(exactText("Кредит по данным карты"));
    private static SelenideElement buttonToBuy = $$("span.button__text").find(exactText("Купить"));
    private static SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement monthField = $("[placeholder='08']");
    private static SelenideElement yearField = $("[placeholder='22']");
    private static SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private static SelenideElement cvcField = $("[placeholder='999']");
    private static SelenideElement button = $$("span.button__text").find(exactText("Продолжить"));
    private static SelenideElement waitSuccessNotification = $(withText("Операция одобрена Банком."));
    private static SelenideElement waitErrorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    private static SelenideElement wrongFormatCard = $(withText("Неверный формат"));
    private static SelenideElement cardDateIsIncorrect = $(withText("Неверно указан срок действия карты"));
    private static SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private static SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));

    public static void enterCardData(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvc);
        button.click();
    }

    public static void enterValidCardData(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
    }

    public static void enterInvalidCardNumber(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getInvalidCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public static void enterInvalidMonth(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getUnrealMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
        cardDateIsIncorrect.waitUntil(Condition.visible, 5000);
    }

    public static void enterInvalidPastYear(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getPastYear(), cardInfo.getOwner(), cardInfo.getCvc());
        cardExpired.waitUntil(Condition.visible, 5000);
    }

    public static void enterInvalidFutureYear(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getFutureYear(), cardInfo.getOwner(), cardInfo.getCvc());
        cardDateIsIncorrect.waitUntil(Condition.visible, 5000);
    }

    public static void enterInvalidCardHolderWithoutSurName(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), DataHelper.generateEnFirstName(), cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public static void enterRusCardHolder(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwnerRus(), cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public static void enterNonCardHolder(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), "", cardInfo.getCvc());
        requiredField.waitUntil(Condition.visible, 5000);
    }

    public static void enterNonCVC(DataHelper.CardInfo cardInfo){
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(),"");
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }


    public static void successNotification() {
        waitSuccessNotification.waitUntil(Condition.visible, 20000);
    }

    public static void errorNotification() {
        waitErrorNotification.waitUntil(Condition.visible, 20000);
    }
}
