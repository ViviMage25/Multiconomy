package me.vivimage25.multiconomy.economy.account;

import java.util.List;
import java.util.UUID;
import me.vivimage25.multiconomy.economy.currency.PhysicalCurrency;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResult;
import me.vivimage25.multiconomy.economy.transaction.result.TransactionResultType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerPhysicalAccount implements Account {

    private boolean active;
    private final UUID uuid;
    private final PhysicalCurrency currency;

    public PlayerPhysicalAccount(UUID uuid, PhysicalCurrency currency) {
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
    public PhysicalCurrency getCurrency() {
        return currency;
    }

    @Override
    public PlayerPhysicalAccount createAccount() {
        active = true;
        return this;
    }

    @Override
    public PlayerPhysicalAccount deleteAccount() {
        active = false;
        return this;
    }

    @Override
    public double getBalance() {
        double balance = 0;
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return 0;
        }
        ItemStack[] inventory = player.getInventory().getStorageContents();
        for (ItemStack item : inventory) {
            if (item == null) {
                continue;
            }
            if (!item.equals(getCurrency().getItem())) {
                continue;
            }
            balance += item.getAmount();
        }
        return balance;
    }

    @Override
    public TransactionResult setBalance(double amount) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return new TransactionResult(0, 0, TransactionResultType.FAILURE, String.format("Player (%s) is not online", Bukkit.getOfflinePlayer(uuid).getName()));
        }
        if (amount > currency.getMaxBalance()) {
            return new TransactionResult(0, getBalance(), TransactionResultType.FAILURE, String.format("Amount cannot exceed max balance of: ".concat(currency.getPrefix()).concat(" %f ").concat(currency.getSuffix()), currency.getMaxBalance()));
        }
        ItemStack[] inventory = player.getInventory().getStorageContents();
        double old_balance = getBalance();
        if (amount > old_balance) {
            if (hasSpaceAvailable(amount, true)) {
                double remaining = amount;
                for (int i = 0; i < inventory.length; i++) {
                    if (inventory[i] == null) {
                        inventory[i] = new ItemStack(currency.getItem());
                        inventory[i].setAmount((int) (remaining >= currency.getItem().getMaxStackSize() ? currency.getItem().getMaxStackSize() : remaining));
                        remaining -= remaining >= currency.getItem().getMaxStackSize() ? currency.getItem().getMaxStackSize() : remaining;
                    } else if (inventory[i].equals(currency.getItem())) {
                        int old_amount = inventory[i].getAmount();
                        inventory[i].setAmount((int) (remaining >= currency.getItem().getMaxStackSize() ? currency.getItem().getMaxStackSize() : remaining));
                        remaining -= remaining >= currency.getItem().getMaxStackSize() - old_amount ? currency.getItem().getMaxStackSize() - old_amount : remaining;
                    }
                }
            }
        } else if (amount < old_balance) {

        }
        player.getInventory().setStorageContents(inventory);
        return new TransactionResult(0, 0, TransactionResultType.SUCCESS, "");
    }

    @Override
    public TransactionResult addAmount(double amount) {
        return new TransactionResult(0, 0, TransactionResultType.SUCCESS, "");
    }

    @Override
    public TransactionResult removeAmount(double amount) {
        return new TransactionResult(0, 0, TransactionResultType.SUCCESS, "");
    }

    private boolean hasSpaceAvailable(double amount, boolean set) {
        double available_space = 0;
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            return false;
        }
        ItemStack[] inventory = player.getInventory().getStorageContents();
        for (ItemStack item : inventory) {
            if (item == null) {
                available_space += currency.getItem().getMaxStackSize();
            }
            if (item.equals(currency.getItem())) {
                available_space += currency.getItem().getMaxStackSize() - item.getAmount();
            }
        }
        return set ? available_space - getBalance() >= amount : available_space >= amount;
    }

}
