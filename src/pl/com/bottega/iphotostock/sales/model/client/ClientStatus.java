package pl.com.bottega.iphotostock.sales.model.client;


public enum ClientStatus {
    STANDARD("Standardowy"),
    VIP("Vip");

    private String statusName;

    ClientStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
