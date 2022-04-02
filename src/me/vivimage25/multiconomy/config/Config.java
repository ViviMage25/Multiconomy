package me.vivimage25.multiconomy.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.vivimage25.multiconomy.Multiconomy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Config {

    private static final Multiconomy PLUGIN = Multiconomy.getInstance();

    private final File file;
    private final YamlConfiguration config;

    public Config(String filename) {
        file = new File(PLUGIN.getDataFolder(), filename);
        config = new YamlConfiguration();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            sync();
            config.save(file);
        } catch (IOException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void loadConfig() {
        try {
            config.load(file);
            setup();
        } catch (IOException | InvalidConfigurationException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void saveDefaultConfig() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            PLUGIN.saveResource(file.getName(), false);
        }
        try {
            config.load(file);
            setup();
        } catch (IOException | InvalidConfigurationException e) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void reloadConfig() {
        saveConfig();
        loadConfig();
    }

    protected abstract void setup();

    protected abstract void sync();

}
