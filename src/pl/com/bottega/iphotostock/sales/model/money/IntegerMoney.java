package pl.com.bottega.iphotostock.sales.model.money;


public class IntegerMoney implements Money {

    private long cents;
    private Currency currency;

    public IntegerMoney(long cents, Currency currency) {
        this.cents = cents;
        this.currency = currency;
    }

    @Override
    public Money add(Money add) {
        IntegerMoney integerMoney = safeConvert(add);
        return new IntegerMoney(cents + integerMoney.cents, currency);
    }

    @Override
    public Money subtract(Money subtrahend) {
        IntegerMoney integerMoney = safeConvert(subtrahend);
        return new IntegerMoney(cents - integerMoney.cents, currency);
    }

    @Override
    public Money multiply(long factor) {
        return new IntegerMoney(factor * cents, currency);
    }

    @Override
    public Money opposite() {
        return new IntegerMoney(-cents, currency);
    }

    @Override
    public RationalMoney convertToRational() {
        Rational rational = Rational.valueOf(cents, 100);
        return new RationalMoney(rational, currency);
    }

    @Override
    public IntegerMoney convertToInteger() {
        return this;
    }

    @Override
    public int compareTo(Money o) {
        IntegerMoney integerMoney = safeConvert(o);
        if (cents == integerMoney.cents)
            return 0;
        else if (cents < integerMoney.cents)
            return -1;
        else
            return 1;
    }

    private void ensureSameCurrency(IntegerMoney other) {
        if (currency != other.currency)
            throw new IllegalArgumentException("Currency missmatch");
    }

    private IntegerMoney safeConvert(Money other) {
        IntegerMoney integerMoney = other.convertToInteger();
        ensureSameCurrency(integerMoney);
        return integerMoney;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof Money))
            return false;
        IntegerMoney integerMoney;
        if (other instanceof RationalMoney)
            integerMoney = ((RationalMoney) other).convertToInteger();
        else
            integerMoney = (IntegerMoney) other;
        return integerMoney.cents == cents && integerMoney.currency == currency;
    }

    @Override
    public String toString() {
        return String.format("%d.%02d %s", cents / 100, cents % 100, currency.name());
    }

    public long toCents() {
        return cents;
    }
}
