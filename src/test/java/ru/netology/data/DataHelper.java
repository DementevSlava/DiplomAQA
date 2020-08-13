package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class CardNumber {
        private String cardNumber;
        private String status;
    }

    public static CardNumber getApprovedCard() {
        return new CardNumber("4444 4444 4444 4441", "APPROVED");
    }

    public static CardNumber getDeclinedCard() {
        return new CardNumber("4444 4444 4444 4442", "DECLINED");
    }

    @Value
    public static class CardInfo {
        private String month;
        private String year;
        private String owner;
        private String cvc;

    }

    public static CardInfo getCardInfo() {
        LocalDate localDate = LocalDate.now();
        String month = String.format("%tm", localDate.plusMonths(1));
        String year = String.format("%ty", localDate.plusYears(1));
        String owner = generateEnName();
        String cvc = getRandomCVC();

        return new CardInfo(month, year, owner, cvc);
    }

    public static String getRandomCVC() {
        int a = 0; // Начальное значение диапазона - "от"
        int b = 999; // Конечное значение диапазона - "до"

        int randomNumber = a + (int) (Math.random() * b);

        return String.format("%03d", randomNumber);
    }


    public static String generateRusName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String generateEnName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }


}
