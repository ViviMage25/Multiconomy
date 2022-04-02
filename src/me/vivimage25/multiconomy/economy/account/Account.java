package me.vivimage25.multiconomy.economy.account;

import java.util.List;
import me.vivimage25.multiconomy.economy.currency.Currency;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;

public interface Account {

    boolean isActive();

    String getName();

    List<String> getMembers();

    boolean addMember(String name);

    boolean removeMember(String name);

    default boolean hasMember(String name) {
        return getMembers().contains(name);
    }

    AccountType getType();

    <T extends Currency> T getCurrency();

    <T extends Account> T createAccount();

    <T extends Account> T deleteAccount();

    default boolean hasAmount(double amount) {
        return getBalance() >= amount;
    }

    default boolean canAddAmount(double amount) {
        return getBalance() + amount <= getCurrency().getMaxBalance();
    }

    double getBalance();

    TransactionResult setBalance(double amount);

    TransactionResult addAmount(double amount);

    TransactionResult removeAmount(double amount);

}
