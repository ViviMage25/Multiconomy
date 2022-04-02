package me.vivimage25.multiconomy.economy.transaction;

import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;

public interface Transaction {
    
    TransactionType getType();
    TransactionResult validate();
    TransactionResult execute();
    
}
