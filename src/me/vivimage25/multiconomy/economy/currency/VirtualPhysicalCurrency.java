package me.vivimage25.multiconomy.economy.currency;

import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class VirtualPhysicalCurrency implements Currency {

    private boolean active;
    private ItemStack item;
    private String prefix;
    private String suffix;
    private double value;
    private double max_balance;

    public VirtualPhysicalCurrency(ItemStack item, String prefix, String suffix, double value, double max_balance) {
        setItem(item);
        setPrefix(prefix);
        setSuffix(suffix);
        setValue(value);
        setMaxBalance(max_balance);
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public VirtualPhysicalCurrency setActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String getName() {
        if (!item.hasItemMeta()) {
            return item.getType().name();
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item.getType().name();
        }
        if (!meta.hasDisplayName()) {
            return item.getType().name();
        }
        return meta.getDisplayName();
    }

    @Override
    public void setName(String name) {
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public List<String> getDescription() {
        if (!item.hasItemMeta()) {
            return List.of("");
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return List.of("");
        }
        if (!meta.hasLore()) {
            return List.of("");
        }
        return meta.getLore();
    }

    @Override
    public void setDescription(String... description) {
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        meta.setLore(List.of(description));
        item.setItemMeta(meta);
    }

    @Override
    public CurrencyType getType() {
        return CurrencyType.VIRTUAL_PHYSICAL;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getMaxBalance() {
        return max_balance;
    }

    @Override
    public void setMaxBalance(double max_balance) {
        this.max_balance = max_balance;
    }

}
