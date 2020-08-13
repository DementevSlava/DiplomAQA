package ru.netology.page;

import com.codeborne.selenide.*;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

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
    private static SelenideElement successNotification = $(withText("Операция одобрена Банком."));
    private static SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

 //   public CardDataEntryPage() {
 //       heading.waitUntil(Condition.exist, 5000);
 //   }

    public static void enterCardData(DataHelper.CardInfo cardInfo, DataHelper.CardNumber cardNumber){
        cardNumberField.setValue(cardNumber.getCardNumber());
        monthField.setValue(cardInfo.getMonth());
        yearField.setValue(cardInfo.getYear());
        ownerField.setValue(cardInfo.getOwner());
        cvcField.setValue(cardInfo.getCvc());
        button.click();
    }

    public static void successNotification(){
        successNotification.waitUntil(Condition.visible, 20000);
    }

    public static void errorNotification(){
        errorNotification.waitUntil(Condition.visible, 20000);
    }
}
