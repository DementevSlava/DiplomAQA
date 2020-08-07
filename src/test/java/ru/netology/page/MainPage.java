package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private SelenideElement buyButton = $$("button").find(Condition.exactText("Купить"));
    private SelenideElement byToCreditButton = $$("button").find(Condition.exactText("Купить в кредит"));

    public void openCardPayPage(){
        buyButton.click();
    }

    public void openCreditPayPage(){
        byToCreditButton.click();
    }
}
