package pl.com.bottega.iphotostock.sales.model;


public enum ClientStatus {
    STANDARD("Standardowy"),
    VIP("Vip"),
    GOLD("Złoty"),
    SILVER("Srebrny"),
    PLATINUM("Platynowy");

    private String statusName;

    ClientStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
