package me.vivimage25.multiconomy.economy.transaction;

import me.vivimage25.multiconomy.economy.account.Account;
import me.vivimage25.multiconomy.economy.currency.Currency;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResultType;

public class ExchangeTransaction implements Transaction {

    private final Account source;
    private final Account target;
    private final double amount;

    public ExchangeTransaction(final Account source, final Account target, final double amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.EXCHANGE;
    }

    @Override
    public TransactionResult validate() {
        if (source == null || target == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Accounts cannot be null");
        }
        if (!source.isActive() || !target.isActive()) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Accounts need to be active");
        }
        if (!source.getCurrency().isActive() || !target.getCurrency().isActive()) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Currencies must be active!");
        }

        Currency source_currency = source.getCurrency();
        Currency target_currency = target.getCurrency();
        double exchange_rate = source_currency.getValue() / target_currency.getValue();

        if (!source.hasAmount(amount)) {
            return new TransactionResult(0, source.getBalance(), TransactionResultType.FAILURE, "Insufficent balance!");
        }
        if (!target.canAddAmount(amount * exchange_rate)) {
            return new TransactionResult(0, target.getBalance(), TransactionResultType.FAILURE, "Overbalance!");
        }
        return new TransactionResult(0, source.getBalance(), TransactionResultType.SUCCESS, "Exchange Validated!");
    }

    @Override
    public TransactionResult execute() {
        Currency source_currency = source.getCurrency();
        Currency target_currency = target.getCurrency();
        double exchange_rate = source_currency.getValue() / target_currency.getValue();

        source.removeAmount(amount);
        target.addAmount(amount * exchange_rate);
        return new TransactionResult(amount, source.getBalance(), TransactionResultType.SUCCESS, "Exchange Complete!");
    }

}
