package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentByCardPage {
    private SelenideElement buttonToBuy = $$("span.button__text" ).find(exactText("Купить" ));
    private SelenideElement heading = $$("h3[class]").find(exactText("Оплата по катре"));
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement owner = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement button = $$("span.button__text" ).find(exactText("Продолжить" ));


}
