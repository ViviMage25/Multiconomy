package me.vivimage25.multiconomy.economy.currency;

public enum CurrencyType {

    VIRTUAL("Virtual"), VIRTUAL_PHYSICAL("Virtual Physical"), PHYSICAL("Physical"), EXPERIENCE("Experience");

    private final String type;

    private CurrencyType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
