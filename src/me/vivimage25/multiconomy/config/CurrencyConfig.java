package me.vivimage25.multiconomy.config;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import me.vivimage25.multiconomy.Multiconomy;
import me.vivimage25.multiconomy.economy.EconomyManager;
import me.vivimage25.multiconomy.economy.currency.CurrencyManager;
import me.vivimage25.multiconomy.economy.currency.CurrencyType;
import me.vivimage25.multiconomy.economy.currency.ExperienceCurrency;
import me.vivimage25.multiconomy.economy.currency.VirtualCurrency;
import me.vivimage25.multiconomy.economy.currency.VirtualPhysicalCurrency;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CurrencyConfig extends Config {

    private final CurrencyManager manager;
    private final Logger log;

    public CurrencyConfig() {
        super("currency.yml");
        manager = EconomyManager.CURRENCY_MANAGER;
        log = Multiconomy.getInstance().getLogger();
    }

    @Override
    protected void setup() {
        setupVirtual();
        setupVirtualPhysical();
        setupPhysical();
        setupExperience();
        setupConfig();
    }

    private void setupConfig() {
        manager.setDefaultCurrency(manager.getCurrency(getConfig().getString("config.default")));

        ConfigurationSection section = getConfig().getConfigurationSection("config.worlds");
        if (section != null) {
            Set<String> keys = section.getKeys(false);
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    String currency = section.getString(key.concat("currency"));
                    manager.setWorldCurrency(key, currency);
                }
            }
        }
    }

    private void setupVirtual() {
        ConfigurationSection section = getConfig().getConfigurationSection("virtual");
        if (section != null) {
            Set<String> keys = section.getKeys(false);
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    ConfigurationSection subsection = section.getConfigurationSection(key);
                    if (subsection == null) {
                        log.warning(String.format("Key %s doesnt exist. Skipping...", key));
                        continue;
                    }

                    boolean active = subsection.getBoolean("active");
                    List<String> description = subsection.getStringList("description");
                    String prefix = subsection.getString("prefix");
                    String suffix = subsection.getString("suffix");
                    double value = subsection.getDouble("value");
                    double max_balance = subsection.getDouble("max-balance");
                    manager.createNewVirtualCurrency(key, prefix, suffix, value, max_balance, (String[]) description.toArray()).setActive(active);
                }
            }
        }
    }

    private void setupVirtualPhysical() {
        ConfigurationSection section = getConfig().getConfigurationSection("virtual-physical");
        if (section != null) {
            Set<String> keys = section.getKeys(false);
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    ConfigurationSection subsection = section.getConfigurationSection(key);
                    if (subsection == null) {
                        log.warning(String.format("Key %s doesnt exist. Skipping...", key));
                        continue;
                    }

                    String material_name = subsection.getString("material");
                    if (material_name == null) {
                        log.warning(String.format("%s in %s is null. skipping...", material_name, key));
                        continue;
                    }

                    Material material = Material.matchMaterial(material_name);
                    if (material == null) {
                        log.warning(String.format("%s in %s is null. skipping...", material_name, key));
                        continue;
                    }

                    List<String> description = subsection.getStringList("description");

                    ItemStack item = new ItemStack(material);
                    ItemMeta meta = item.getItemMeta();
                    if (meta == null) {
                        log.warning(String.format("cannot get metadata from %s. skipping...", key));
                        continue;
                    }

                    meta.setDisplayName(key);
                    meta.setLore(description);
                    item.setItemMeta(meta);

                    boolean active = subsection.getBoolean("active");
                    String prefix = subsection.getString("prefix");
                    String suffix = subsection.getString("suffix");
                    double value = subsection.getDouble("value");
                    double max_balance = subsection.getDouble("max-balance");

                    manager.createNewVirtualPhysicalCurrency(item, prefix, suffix, value, max_balance).setActive(active);
                }
            }
        }
    }

    private void setupPhysical() {
        ConfigurationSection section = getConfig().getConfigurationSection("physical");
        if (section != null) {
            Set<String> keys = section.getKeys(false);
            if (!keys.isEmpty()) {
                for (String key : keys) {
                    ConfigurationSection subsection = section.getConfigurationSection(key);
                    if (subsection == null) {
                        log.warning(String.format("Key %s doesnt exist. Skipping...", key));
                        continue;
                    }

                    String material_name = subsection.getString("material");
                    if (material_name == null) {
                        log.warning(String.format("%s in %s is null. skipping...", material_name, key));
                        continue;
                    }

                    Material material = Material.matchMaterial(material_name);
                    if (material == null) {
                        log.warning(String.format("%s in %s is null. skipping...", material_name, key));
                        continue;
                    }

                    List<String> description = subsection.getStringList("description");

                    ItemStack item = new ItemStack(material);
                    ItemMeta meta = item.getItemMeta();
                    if (meta == null) {
                        log.warning(String.format("cannot get metadata from %s. skipping...", key));
                        continue;
                    }

                    meta.setDisplayName(key);
                    meta.setLore(description);
                    item.setItemMeta(meta);

                    boolean active = subsection.getBoolean("active");
                    String prefix = subsection.getString("prefix");
                    String suffix = subsection.getString("suffix");
                    double value = subsection.getDouble("value");

                    manager.createNewPhysicalCurrency(item, prefix, suffix, value).setActive(active);
                }
            }
        }
    }

    private void setupExperience() {
        ConfigurationSection section = getConfig().getConfigurationSection("experience");
        if (section != null) {
            boolean active = section.getBoolean("active");
            double value = section.getDouble("value");

            manager.createExperienceCurrency(value).setActive(active);
        }
    }

    @Override
    protected void sync() {
        syncConfig();
        syncVirtual();
        syncVirtualPhysical();
        syncPhysical();
        syncExperience();
    }

    private void syncConfig() {
        getConfig().set("config.default", manager.getDefaultCurrencyName());

        Set<String> keys = manager.getWorldCurrencyMap().keySet();
        if (!keys.isEmpty()) {
            ConfigurationSection section = getConfig().createSection("config.worlds");
            for (String key : keys) {
                section.set(key.concat("currency"), manager.getWorldCurrency(key));
            }
        }
    }

    private void syncVirtual() {
        ConfigurationSection section = getConfig().createSection("virtual");
        Set<String> keys = manager.getCurrencyMap().keySet();
        if (!keys.isEmpty()) {
            for (String key : keys) {
                if (manager.getCurrency(key).getType() == CurrencyType.VIRTUAL) {
                    VirtualCurrency currency = manager.getCurrency(key);
                    section.set(key.concat(".active"), Boolean.toString(currency.isActive()));
                    section.set(key.concat(".description"), currency.getDescription());
                    section.set(key.concat(".prefix"), currency.getPrefix());
                    section.set(key.concat(".suffix"), currency.getSuffix());
                    section.set(key.concat(".value"), currency.getValue());
                    section.set(key.concat(".max-balance"), currency.getMaxBalance());
                }
            }
        }
    }

    private void syncVirtualPhysical() {
        ConfigurationSection section = getConfig().createSection("virtual-physical");
        Set<String> keys = manager.getCurrencyMap().keySet();
        if (!keys.isEmpty()) {
            for (String key : keys) {
                if (manager.getCurrency(key).getType() == CurrencyType.VIRTUAL_PHYSICAL) {
                    VirtualPhysicalCurrency currency = manager.getCurrency(key);
                    section.set(key.concat(".active"), Boolean.toString(currency.isActive()));
                    section.set(key.concat(".material"), currency.getItem().getType().name());
                    section.set(key.concat(".description"), currency.getDescription());
                    section.set(key.concat(".prefix"), currency.getPrefix());
                    section.set(key.concat(".suffix"), currency.getSuffix());
                    section.set(key.concat(".value"), currency.getValue());
                    section.set(key.concat(".max-balance"), currency.getMaxBalance());
                }
            }
        }
    }

    private void syncPhysical() {
        ConfigurationSection section = getConfig().createSection("physical");
        Set<String> keys = manager.getCurrencyMap().keySet();
        if (!keys.isEmpty()) {
            for (String key : keys) {
                if (manager.getCurrency(key).getType() == CurrencyType.PHYSICAL) {
                    VirtualPhysicalCurrency currency = manager.getCurrency(key);
                    section.set(key.concat(".active"), Boolean.toString(currency.isActive()));
                    section.set(key.concat(".material"), currency.getItem().getType().name());
                    section.set(key.concat(".description"), currency.getDescription());
                    section.set(key.concat(".prefix"), currency.getPrefix());
                    section.set(key.concat(".suffix"), currency.getSuffix());
                    section.set(key.concat(".value"), currency.getValue());
                }
            }
        }
    }

    private void syncExperience() {
        ConfigurationSection section = getConfig().createSection("experience");
        if (section != null) {
            ExperienceCurrency currency = manager.getCurrency("experience");
            section.set("active", Boolean.toString(currency.isActive()));
            section.set("value", currency.getValue());
        }
    }

}
