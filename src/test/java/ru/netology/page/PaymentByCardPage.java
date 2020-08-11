package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentByCardPage {


    private SelenideElement heading = $$("h3[class]").find(exactText("Оплата по карте"));
    private SelenideElement buttonToBuy = $$("span.button__text").find(exactText("Купить"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement button = $$("span.button__text").find(exactText("Продолжить"));


    public PaymentByCardPage() {
        heading.waitUntil(Condition.exist, 5000);
    }

    public void enterValidDataApprovedCard(DataHelper.CardInfo cardInfo){
        cardNumberField.setValue(DataHelper.getApprovedCard().getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCvc());
        button.click();
    }

    public void enterValidDataDeclinedCard(DataHelper.CardInfo cardInfo){
        cardNumberField.setValue(DataHelper.getDeclinedCard().getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCvc());
        button.click();
    }
}
