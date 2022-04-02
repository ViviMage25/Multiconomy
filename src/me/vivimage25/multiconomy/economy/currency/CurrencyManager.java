package me.vivimage25.multiconomy.economy.currency;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CurrencyManager {

    private final Map<String, Currency> CURRENCY_MAP;
    private Currency default_currency;
    private final Map<String, Currency> WORLD_CURRENCY_MAP;

    public CurrencyManager() {
        CURRENCY_MAP = new HashMap<>();
        WORLD_CURRENCY_MAP = new HashMap<>();
    }

    public Map<String, Currency> getWorldCurrencyMap() {
        return WORLD_CURRENCY_MAP;
    }

    public String getWorldCurrencyName(String name) {
        return getWorldCurrency(name).getName();
    }

    public <T extends Currency> T getWorldCurrency(String name) {
        return (T) WORLD_CURRENCY_MAP.get(name);
    }

    public <T extends Currency> T setWorldCurrency(String name, String currency_name) {
        return setWorldCurrency(name, CURRENCY_MAP.get(currency_name));
    }

    public <T extends Currency> T setWorldCurrency(String name, Currency currency) {
        return (T) WORLD_CURRENCY_MAP.put(name, currency);
    }

    public <T extends Currency> T removeWorldCurrency(String name) {
        return (T) WORLD_CURRENCY_MAP.remove(name);
    }

    public String getDefaultCurrencyName() {
        return getDefaultCurrency().getName();
    }

    public <T extends Currency> T getDefaultCurrency() {
        return (T) default_currency;
    }

    public <T extends Currency> void setDefaultCurrency(T currency) {
        if (!hasCurrency(currency.getName())) {
            return;
        }
        default_currency = currency;
    }

    public Map<String, Currency> getCurrencyMap() {
        return CURRENCY_MAP;
    }

    public boolean hasCurrency(String name) {
        return CURRENCY_MAP.containsKey(name);
    }

    public <T extends Currency> T getCurrency(String name) {
        return (T) CURRENCY_MAP.get(name);
    }

    public <T extends Currency> T removeCurrency(String name) {
        return (T) CURRENCY_MAP.remove(name);
    }

    public <T extends Currency> boolean renameCurrency(String old_name, String new_name) {
        if (!hasCurrency(old_name)) {
            return false;
        }
        T currency = getCurrency(old_name);
        if (currency.getType().equals(CurrencyType.EXPERIENCE)) {
            return false;
        }
        if (hasCurrency(new_name)) {
            return false;
        }
        CURRENCY_MAP.putIfAbsent(new_name, currency);
        return true;
    }

    public VirtualCurrency createNewVirtualCurrency(String name, String prefix, String suffix, double value, double max_balance, String... description) {
        if (hasCurrency(name)) {
            return null;
        }
        VirtualCurrency currency = new VirtualCurrency(name, prefix, suffix, value, max_balance, description);
        CURRENCY_MAP.putIfAbsent(name, currency);
        if (getDefaultCurrency() == null) {
            setDefaultCurrency(currency);
        }
        return currency;
    }

    public VirtualPhysicalCurrency createNewVirtualPhysicalCurrency(ItemStack item, String prefix, String suffix, double value, double max_balance) {
        String name = item.getType().name();
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                name = meta.getDisplayName();
            }
        }
        if (hasCurrency(name)) {
            return null;
        }
        VirtualPhysicalCurrency currency = new VirtualPhysicalCurrency(item, prefix, suffix, value, max_balance);
        CURRENCY_MAP.putIfAbsent(name, currency);
        if (getDefaultCurrency() == null) {
            setDefaultCurrency(currency);
        }
        return currency;
    }

    public PhysicalCurrency createNewPhysicalCurrency(ItemStack item, String prefix, String suffix, double value) {
        String name = item.getType().name();
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                name = meta.getDisplayName();
            }
        }
        if (hasCurrency(name)) {
            return null;
        }
        PhysicalCurrency currency = new PhysicalCurrency(item, prefix, suffix, value);
        CURRENCY_MAP.putIfAbsent(name, currency);
        if (getDefaultCurrency() == null) {
            setDefaultCurrency(currency);
        }
        return currency;
    }

    public ExperienceCurrency createExperienceCurrency(double value) {
        if (hasCurrency("experience")) {
            return null;
        }
        ExperienceCurrency currency = new ExperienceCurrency(value);
        CURRENCY_MAP.putIfAbsent("experience", currency);
        if (getDefaultCurrency() == null) {
            setDefaultCurrency(currency);
        }
        return currency;
    }

}
