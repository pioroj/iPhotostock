package pl.com.bottega.iphotostock.sales.model.client;


public class CantAffordException extends RuntimeException {
    public CantAffordException(String message) {
        super(message);
    }
}
