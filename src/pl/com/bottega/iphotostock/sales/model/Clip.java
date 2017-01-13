package pl.com.bottega.iphotostock.sales.model;


import pl.com.bottega.iphotostock.sales.model.money.Money;

public class Clip extends AbstractProduct {

    private Long length;
    private static final long FIVE_MINUTES = 1000L * 60 * 5;

    public Clip(boolean active, String name, Money catalogPrice, String number, Long length) {
        super(active, name, catalogPrice, number);
        this.length = length;
    }

    public Clip(String name, Money catalogPrice, String number, Long length) {
        this(true, name, catalogPrice, number, length);
    }

    @Override
    public Money calculatePrice(Client client) {
        if (length > FIVE_MINUTES)
            return catalogPrice.multiply(2);
        return catalogPrice;
    }


}
