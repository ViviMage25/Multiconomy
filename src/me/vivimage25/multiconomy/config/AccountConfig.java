package me.vivimage25.multiconomy.config;

import java.util.logging.Logger;
import me.vivimage25.multiconomy.Multiconomy;
import me.vivimage25.multiconomy.economy.EconomyManager;
import me.vivimage25.multiconomy.economy.account.AccountManager;

public class AccountConfig extends Config {

    private final AccountManager manager;
    private final Logger log;

    public AccountConfig() {
        super("accounts.yml");
        manager = EconomyManager.ACCOUNT_MANAGER;
        log = Multiconomy.getInstance().getLogger();
    }

    @Override
    protected void setup() {
    }

    @Override
    protected void sync() {
    }

}
