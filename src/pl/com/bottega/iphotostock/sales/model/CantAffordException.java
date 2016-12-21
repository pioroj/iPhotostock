package pl.com.bottega.iphotostock.sales.model;


public class CantAffordException extends RuntimeException {
    public CantAffordException(String message) {
        super(message);
    }
}
