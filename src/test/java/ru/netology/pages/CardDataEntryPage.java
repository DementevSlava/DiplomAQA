package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDataEntryPage {
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement button = $$("span.button__text").find(exactText("Продолжить"));
    private SelenideElement waitSuccessNotification = $(withText("Операция одобрена Банком."));
    private SelenideElement waitErrorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    private SelenideElement wrongFormatCard = $(withText("Неверный формат"));
    private SelenideElement cardDateIsIncorrect = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));

    public void enterCardData(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvc);
        button.click();
    }

    public void enterApprovedCardData(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
    }

    public void enterDeclinedCardData(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getDeclinedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
    }

    public void enterInvalidCardNumber(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getInvalidCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public void enterInvalidMonth(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getUnrealMonth(), cardInfo.getYear(), cardInfo.getOwner(), cardInfo.getCvc());
        cardDateIsIncorrect.waitUntil(Condition.visible, 5000);
    }

    public void enterInvalidPastYear(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getPastYear(), cardInfo.getOwner(), cardInfo.getCvc());
        cardExpired.waitUntil(Condition.visible, 5000);
    }

    public void enterInvalidFutureYear(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getFutureYear(), cardInfo.getOwner(), cardInfo.getCvc());
        cardDateIsIncorrect.waitUntil(Condition.visible, 5000);
    }

    public void enterInvalidCardHolderWithoutSurName(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), DataHelper.generateEnFirstName(), cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public void enterRusCardHolder(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwnerRus(), cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public void enterNonCardHolder(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), "", cardInfo.getCvc());
        requiredField.waitUntil(Condition.visible, 5000);
    }

    public void enterSymbolCardHolder(DataHelper.CardInfo cardInfo) {
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), "!@#$%^&*()_+", cardInfo.getCvc());
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public void enterNonCVC(DataHelper.CardInfo cardInfo){
        enterCardData(DataHelper.getApprovedCard().getCardNumber(), cardInfo.getMonth(), cardInfo.getYear(), cardInfo.getOwner(),"");
        wrongFormatCard.waitUntil(Condition.visible, 5000);
    }

    public void successNotification() {
        waitSuccessNotification.waitUntil(Condition.visible, 20000);
    }

    public void errorNotification() {
        waitErrorNotification.waitUntil(Condition.visible, 20000);
    }
}
