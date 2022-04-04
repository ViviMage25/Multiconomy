package me.vivimage25.multiconomy;

import me.vivimage25.multiconomy.config.CurrencyConfig;
import me.vivimage25.multiconomy.economy.dependancy.VaultEconomyProvider;
import me.vivimage25.multiconomy.listener.MainMenuGUIListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Multiconomy extends JavaPlugin {

    private static Multiconomy instance;
    private VaultEconomyProvider economy_provider;
    private CurrencyConfig currency_config;
    private MainMenuGUIListener main_menu_gui;

    public static Multiconomy getInstance() {
        return instance;
    }

    public VaultEconomyProvider getEconomyProvider() {
        return economy_provider;
    }

    private void instanceFields() {
        Multiconomy.instance = this;
        economy_provider = new VaultEconomyProvider();
        currency_config = new CurrencyConfig();
        main_menu_gui = new MainMenuGUIListener();
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(main_menu_gui, this);
    }

    private void registerVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault not found... Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getServicesManager().register(Economy.class, getEconomyProvider(), this, ServicePriority.Highest);
        getLogger().info(String.format("VaultAPI Economy registered to %s", getName()));
    }

    private void unregisterVault() {
        if (Bukkit.getServicesManager().getRegistration(Economy.class).getProvider() instanceof VaultEconomyProvider) {
            Bukkit.getServicesManager().unregister(Economy.class, getEconomyProvider());
            getLogger().info(String.format("VaultAPI Economy unregistered from %s", getName()));
        }
    }

    private void loadConfigs() {
        currency_config.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instanceFields();
        registerVault();
        loadConfigs();
    }

    @Override
    public void onDisable() {
        unregisterVault();
    }

}
