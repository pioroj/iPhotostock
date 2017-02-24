package pl.com.bottega.iphotostock.sales.model.product;


import pl.com.bottega.iphotostock.sales.model.product.Product;

public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException(Product product) {
        super(String.format("Product %s is not available", product.getNumber()));
    }

}
