package me.vivimage25.multiconomy.economy.account;

public enum AccountType {
    NPC("NPC"), PLAYER("Player"), BANK("Bank"), SERVER("Server"), OTHER("Other");

    private final String type;

    private AccountType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
