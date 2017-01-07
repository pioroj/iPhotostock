package pl.com.bottega.iphotostock.sales.presentation;


import pl.com.bottega.iphotostock.sales.money.Rational;
import pl.com.bottega.iphotostock.sales.money.RationalMoney;
import pl.com.bottega.iphotostock.sales.money.IntegerMoney;

import static pl.com.bottega.iphotostock.sales.money.Money.Currency.CREDIT;

public class MoneyEqualsTest {

    public static void main(String[] args) {

        System.out.println("True: ");
        IntegerMoney intMon1 = new IntegerMoney(0, CREDIT);
        RationalMoney ratMon1 = new RationalMoney(Rational.valueOf(0),CREDIT);

        System.out.println(intMon1.equals(ratMon1));
        System.out.println(intMon1.equals(intMon1));
        System.out.println(new IntegerMoney(150, CREDIT).equals(new RationalMoney(Rational.valueOf(3,2),CREDIT)));
        System.out.println(new RationalMoney(Rational.valueOf(2), CREDIT).equals(new RationalMoney(Rational.valueOf(2), CREDIT)));

    }

}
