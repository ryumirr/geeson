package module.enums;

public enum ShippingStatus {
    REQUESTED("Requested"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered");

    private final String label;

    ShippingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}