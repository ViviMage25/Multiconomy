package me.vivimage25.multiconomy.economy.account;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.vivimage25.multiconomy.economy.currency.VirtualCurrency;

public class AccountManager {

    private final Map<String, Account> ACCOUNT_MAP;

    public AccountManager() {
        ACCOUNT_MAP = new HashMap<>();
    }

    public boolean hasAccount(String name) {
        return ACCOUNT_MAP.containsKey(name);
    }

    public <T extends Account> T getAccount(String name) {
        return (T) ACCOUNT_MAP.get(name);
    }

    public <T extends Account> T removeAccount(String name) {
        return (T) ACCOUNT_MAP.remove(name);
    }

    public PlayerVirtualAccount createNewPlayerVirtualAccount(UUID uuid, VirtualCurrency currency) {
        String name = uuid.toString();
        if (hasAccount(name)) {
            return null;
        }
        PlayerVirtualAccount account = new PlayerVirtualAccount(uuid, currency);
        account.createAccount();
        ACCOUNT_MAP.putIfAbsent(name, account);
        return account;
    }

}
