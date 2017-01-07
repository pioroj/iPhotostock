package pl.com.bottega.iphotostock.sales.money;


//TODO equals i hashcode
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
        return new IntegerMoney(cents * factor, currency);
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
        if(cents == integerMoney.cents)
            return 0;
        else if (cents < integerMoney.cents)
            return -1;
        else
            return 1;
    }

    private void ensureSameCurrency(IntegerMoney other) {
        if(currency != other.currency)
            throw new IllegalArgumentException("Currency mismatch.");
    }

    private IntegerMoney safeConvert(Money other) {
        IntegerMoney integerMoney = other.convertToInteger();
        ensureSameCurrency(integerMoney);
        return integerMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        //if (!(o instanceof IntegerMoney)) return false;

        if (o instanceof RationalMoney) {
            RationalMoney that = (RationalMoney) o;
            if (cents != that.convertToInteger().cents) return false;
            return currency == that.convertToInteger().currency;
        } else {
            IntegerMoney that = (IntegerMoney) o;
            if (cents != that.cents) return false;
            return currency == that.currency;
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (cents ^ (cents >>> 32));
        result = 31 * result + currency.hashCode();
        return result;
    }

    public String toString(){
        return cents / 100 + " " + currency.name();
    }
}
