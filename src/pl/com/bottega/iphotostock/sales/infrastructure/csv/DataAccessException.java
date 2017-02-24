package pl.com.bottega.iphotostock.sales.infrastructure.csv;


public class DataAccessException extends RuntimeException {

    public DataAccessException(Exception ex) {
        super(ex);
    }

}
