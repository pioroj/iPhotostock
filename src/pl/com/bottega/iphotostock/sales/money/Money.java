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
        return new RationalMoney(value, currency);
    }

    public static Money valueOf(long value, Currency currency) {
        return new RationalMoney(Rational.valueOf(value), currency);
    }

    public static Money valueOf(long value) {
        return new RationalMoney(Rational.valueOf(value), DEFAULT_CURRENCY);
    }
}
