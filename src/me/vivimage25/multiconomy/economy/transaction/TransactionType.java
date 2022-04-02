package me.vivimage25.multiconomy.economy.transaction;

public enum TransactionType {

    TRANSFER("Transfer"), EXCHANGE("Exchange"), TRADE("Trade");

    private final String type;

    private TransactionType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
