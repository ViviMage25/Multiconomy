package me.vivimage25.multiconomy.economy.transaction.result;

public enum TransactionResultType {
    SUCCESS("Success"), FAILURE("Failure"), UNKNOWN("Unknown");

    private final String type;

    private TransactionResultType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
