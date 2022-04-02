package me.vivimage25.multiconomy.economy.account;

import java.util.List;
import java.util.UUID;
import me.vivimage25.multiconomy.economy.currency.VirtualCurrency;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResultType;
import org.bukkit.Bukkit;

public class PlayerVirtualAccount implements Account {

    private boolean active;
    private final UUID uuid;
    private final VirtualCurrency currency;
    private double balance;

    public PlayerVirtualAccount(UUID uuid, VirtualCurrency currency) {
        this.uuid = uuid;
        this.currency = currency;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getName() {
        return uuid.toString();
    }

    @Override
    public List<String> getMembers() {
        return List.of(Bukkit.getOfflinePlayer(uuid).getName());
    }

    @Override
    public boolean addMember(String name) {
        return true;
    }

    @Override
    public boolean removeMember(String name) {
        return true;
    }

    @Override
    public AccountType getType() {
        return AccountType.PLAYER;
    }

    @Override
    public VirtualCurrency getCurrency() {
        return currency;
    }

    @Override
    public PlayerVirtualAccount createAccount() {
        balance = 0;
        active = true;
        return this;
    }

    @Override
    public PlayerVirtualAccount deleteAccount() {
        active = false;
        return this;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public TransactionResult setBalance(double amount) {
        if (currency.getMaxBalance() <= amount) {
            return new TransactionResult(0, balance, TransactionResultType.FAILURE, "Amount cannot exceed Max Balance");
        }
        double old_balance = getBalance();
        balance = amount;
        return new TransactionResult(amount - old_balance, balance, TransactionResultType.SUCCESS, "Balance Set!");
    }

    @Override
    public TransactionResult addAmount(double amount) {
        if (!canAddAmount(amount)) {
            return new TransactionResult(0, balance, TransactionResultType.FAILURE, "Amount cannot exceed Max Balance");
        }
        balance += amount;
        return new TransactionResult(amount, balance, TransactionResultType.SUCCESS, "Balance Added!");
    }

    @Override
    public TransactionResult removeAmount(double amount) {
        if (!hasAmount(amount)) {
            return new TransactionResult(0, balance, TransactionResultType.FAILURE, "Amount cannot drop under 0");
        }
        balance -= amount;
        return new TransactionResult(-amount, balance, TransactionResultType.SUCCESS, "Balance Removed!");
    }

}
