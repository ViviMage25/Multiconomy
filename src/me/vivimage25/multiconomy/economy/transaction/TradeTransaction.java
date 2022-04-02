package me.vivimage25.multiconomy.economy.transaction;

import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResultType;

public class TradeTransaction implements Transaction {

    private final Transaction source;
    private final Transaction target;

    public TradeTransaction(final Transaction source, final Transaction target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.TRADE;
    }

    @Override
    public TransactionResult validate() {
        if (source == null || target == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Transactions cannot be null");
        }
        if (source.validate().getType() != TransactionResultType.SUCCESS) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Source Failure!");
        }
        if (target.validate().getType() != TransactionResultType.SUCCESS) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Target Failure!");
        }
        return new TransactionResult(0, 0, TransactionResultType.SUCCESS, "Trade Validated!");
    }

    @Override
    public TransactionResult execute() {
        source.execute();
        target.execute();
        return new TransactionResult(0, 0, TransactionResultType.SUCCESS, "Trade Completed!");
    }

}
