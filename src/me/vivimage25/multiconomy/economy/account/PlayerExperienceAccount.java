package me.vivimage25.multiconomy.economy.account;

import java.util.List;
import java.util.UUID;
import me.vivimage25.multiconomy.economy.currency.ExperienceCurrency;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResultType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerExperienceAccount implements Account {

    private boolean active;
    private final UUID uuid;
    private final ExperienceCurrency currency;

    public PlayerExperienceAccount(UUID uuid, ExperienceCurrency currency) {
        this.uuid = uuid;
        this.currency = currency;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public String getName() {
        return Bukkit.getOfflinePlayer(uuid).getName();
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
    public ExperienceCurrency getCurrency() {
        return currency;
    }

    @Override
    public PlayerExperienceAccount createAccount() {
        active = true;
        return this;
    }

    @Override
    public PlayerExperienceAccount deleteAccount() {
        active = false;
        return this;
    }

    @Override
    public double getBalance() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return 0;
        }
        return player.getLevel();
    }

    @Override
    public TransactionResult setBalance(double amount) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Player must be online.");
        }
        if (currency.getMaxBalance() <= amount) {
            return new TransactionResult(0, player.getLevel(), TransactionResultType.FAILURE, "Amount cannot exceed Max Balance");
        }
        double difference = (player.getLevel() >= amount) ? (double) (player.getLevel() - amount) : (double) (amount - player.getLevel());
        player.setLevel((int) amount);
        return new TransactionResult(difference, player.getLevel(), TransactionResultType.SUCCESS, "Balance Set!");
    }

    @Override
    public TransactionResult addAmount(double amount) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Player must be online.");
        }
        if (!canAddAmount(amount)) {
            return new TransactionResult(0, player.getLevel(), TransactionResultType.FAILURE, "Amount cannot exceed Max Balance");
        }
        player.setLevel((int) (player.getLevel() + amount));
        return new TransactionResult(amount, player.getLevel(), TransactionResultType.SUCCESS, "Balance Added!");
    }

    @Override
    public TransactionResult removeAmount(double amount) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, "Player must be online.");
        }
        if (!hasAmount(amount)) {
            return new TransactionResult(0, player.getLevel(), TransactionResultType.FAILURE, "Amount cannot drop under 0");
        }
        player.setLevel((int) (player.getLevel() - amount));
        return new TransactionResult(amount, player.getLevel(), TransactionResultType.SUCCESS, "Balance Removed!");
    }

}
