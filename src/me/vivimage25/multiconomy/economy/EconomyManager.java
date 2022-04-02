package me.vivimage25.multiconomy.economy;

import me.vivimage25.multiconomy.economy.account.AccountManager;
import me.vivimage25.multiconomy.economy.currency.CurrencyManager;

public class EconomyManager {
    
    public static final CurrencyManager CURRENCY_MANAGER = new CurrencyManager();
    public static final AccountManager ACCOUNT_MANAGER = new AccountManager();
    
    private EconomyManager() {}
    
}
