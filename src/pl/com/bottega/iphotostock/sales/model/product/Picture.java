package pl.com.bottega.iphotostock.sales.model.product;


import pl.com.bottega.iphotostock.sales.model.client.Client;
import pl.com.bottega.iphotostock.sales.model.money.Money;

import java.util.Collection;
import java.util.HashSet;

public class Picture extends AbstractProduct {

    private Collection<String> tags;

    public Picture(String number, String name, Collection<String> tags, Money catalogPrice, boolean active) {
        super(active, name, catalogPrice, number);
        this.tags = new HashSet<String>(tags); //nie robić = tags - niebezpieczne!
    }

    public Picture(String number, String name, Collection<String> tags, Money catalogPrice) {
        this(number, name, tags, catalogPrice, true);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    /*@Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof Picture))
            return false;
        Picture otherPicture = (Picture) other;
        return (otherPicture.number == this.number) ||
                (otherPicture.number != null && otherPicture.number.equals(this.number));
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;

        Picture picture = (Picture) o;

        return number != null ? number.equals(picture.number) : picture.number == null;

    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }

    @Override
    public Money calculatePrice(Client client) {
        return catalogPrice;
    }
}
