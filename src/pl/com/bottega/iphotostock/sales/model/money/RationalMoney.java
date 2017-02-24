package pl.com.bottega.iphotostock.sales.model.money;


public class RationalMoney implements Money {

    private final Rational value;
    private final Currency currency;

    public RationalMoney(Rational value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money opposite() {
        return new RationalMoney(value.negative(), currency);
    }

    public Money add(Money addend) {
        RationalMoney rationalMoney = addend.convertToRational();
        if (currency != rationalMoney.currency)
            throw new IllegalArgumentException("The currencies do not match.");
        return new RationalMoney(value.add(rationalMoney.value), currency);
    }

    public Money subtract(Money subtrahend) {
        RationalMoney rationalMoney = subtrahend.convertToRational();
        if (currency != rationalMoney.currency)
            throw new IllegalArgumentException("The currencies do not match.");
        return new RationalMoney(value.subtract(rationalMoney.value), currency);
    }

    public Money multiply(long factor) {
        if (factor < 0)
            throw new IllegalArgumentException("RationalMoney cannot be negative");
        return new RationalMoney(value.multiply(factor), currency);
    }
    public boolean gte(Money other) {
        return compareTo(other) >= 0;
    }
    public boolean gt(Money other) {
        return compareTo(other) > 0;
    }
    public boolean lte(Money other) {
        return compareTo(other) <= 0;
    }

    public boolean lt(Money other) {
         return compareTo(other) < 0;
    }

    public int compareTo(Money other) {
        RationalMoney rationalMoney = other.convertToRational();
        if (!rationalMoney.currency.equals(currency))
            throw new IllegalArgumentException("Currency mismatch");
        return value.compareTo(rationalMoney.value);
    }

    @Override
    public String toString() {
        return value.toDouble() + " " + currency.name();
    }


    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Money))
            return false;
        RationalMoney rationalMoney;
        if (other instanceof IntegerMoney)
            rationalMoney = ((IntegerMoney) other).convertToRational();
        else
            rationalMoney = (RationalMoney) other;
        return rationalMoney.value.equals(value) && rationalMoney.currency == currency;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public RationalMoney convertToRational() {
        return this;
    }

    @Override
    public IntegerMoney convertToInteger() {
        long cents = value.getNumerator() * 100 / value.getDenominator();
        return new IntegerMoney(cents, currency);
    }
}