package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private static SelenideElement buyButton = $$("button").find(Condition.exactText("Купить"));
    private static SelenideElement byToCreditButton = $$("button").find(Condition.exactText("Купить в кредит"));

    public static void openCardPayPage(){
        buyButton.click();
    }

    public static void openCreditPayPage(){
        byToCreditButton.click();
    }
}
