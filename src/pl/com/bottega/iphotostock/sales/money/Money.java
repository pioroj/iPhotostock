package pl.com.bottega.iphotostock.sales.money;


public interface Money extends Comparable<Money> {

    enum Currency {CREDIT}

    Currency DEFAULT_CURRENCY = Currency.CREDIT;

    Money ZERO = valueOf(0, DEFAULT_CURRENCY);

    Money add(Money add);

    Money subtract(Money subtrahend);

    Money multiply(long factor);

    default boolean gte(Money other) {
        return compareTo(other) >= 0;
    }
    default boolean gt(Money other) {
        return compareTo(other) > 0;
    }
    default boolean lte(Money other) {
        return compareTo(other) <= 0;
    }
    default boolean lt(Money other) {
        return compareTo(other) < 0;
    }

    Money opposite();

    RationalMoney convertToRational();

    IntegerMoney convertToInteger();

    public static Money valueOf(Rational value, Currency currency) {
        //return new RationalMoney(value, currency).convertToInteger();
        return new IntegerMoney(value.getNumerator() * 100L / value.getDenominator(), currency);
    }

    public static Money valueOf(long value, Currency currency) {
        //return new RationalMoney(Rational.valueOf(value), currency).convertToInteger();
        return new IntegerMoney(value * 100L, currency);
    }

    public static Money valueOf(long value) {
        //return new RationalMoney(Rational.valueOf(value), DEFAULT_CURRENCY).convertToInteger();
        return new IntegerMoney(value * 100, DEFAULT_CURRENCY);
    }

    public static Money valueOf(float value) {
        return new IntegerMoney((long) (value * 100.0), DEFAULT_CURRENCY);
    }

}
