package me.vivimage25.multiconomy.economy.transaction;

import me.vivimage25.multiconomy.economy.account.Account;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResultType;

public class TransferTransaction implements Transaction {

    private final Account source;
    private final Account target;
    private final double amount;

    public TransferTransaction(final Account source, final Account target, double amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRANSFER;
    }

    @Override
    public TransactionResult validate() {
        if (source == null || target == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Accounts cannot be null!");
        }
        if (!source.isActive() || !target.isActive()) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Accounts need to be active!");
        }
        if (!source.getCurrency().isActive() || !target.getCurrency().isActive()) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Currencies must be active!");
        }
        if (source.getCurrency() != target.getCurrency()) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Currencies not equal!");
        }
        if (!source.hasAmount(amount)) {
            return new TransactionResult(0, source.getBalance(), TransactionResultType.FAILURE, "Insufficent balance!");
        }
        if (!target.canAddAmount(amount)) {
            return new TransactionResult(0, target.getBalance(), TransactionResultType.FAILURE, "Overbalance!");
        }
        return new TransactionResult(0, source.getBalance(), TransactionResultType.SUCCESS, "Transfer Validated!");
    }

    @Override
    public TransactionResult execute() {
        source.removeAmount(amount);
        target.addAmount(amount);
        return new TransactionResult(amount, source.getBalance(), TransactionResultType.SUCCESS, "Transfer Complete!");
    }

}
