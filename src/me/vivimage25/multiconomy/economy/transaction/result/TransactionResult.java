package me.vivimage25.multiconomy.economy.transaction.result;

public class TransactionResult {

    private final double amount;
    private final double balance;
    private final TransactionResultType type;
    private final String[] message;

    public TransactionResult(double amount, double balance, TransactionResultType type, String... message) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public TransactionResultType getType() {
        return type;
    }

    public String[] getMessage() {
        return message;
    }

}
