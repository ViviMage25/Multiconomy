package me.vivimage25.multiconomy.util;

import java.util.HashMap;
import java.util.Map;

public class GlobalStrings {

    public static final Map<String, String> PERMISSION_MAP = new HashMap<>();

    static {
        PERMISSION_MAP.put("gui_access", "multiconomy.gui.access");
        PERMISSION_MAP.put("gui_balance", "multiconomy.gui.balance");
        PERMISSION_MAP.put("gui_balance_other", "multiconomy.gui.balance.other");
        PERMISSION_MAP.put("gui_balancetop", "multiconomy.gui.balancetop");
        PERMISSION_MAP.put("gui_worth", "multiconomy.gui.worth");
        PERMISSION_MAP.put("gui_worth_other", "multiconomy.gui.worth.other");
        PERMISSION_MAP.put("gui_worthtop", "multiconomy.gui.worthtop");
        PERMISSION_MAP.put("gui_pay", "multiconomy.gui.pay");
        PERMISSION_MAP.put("gui_trade", "multiconomy.gui.trade");
        PERMISSION_MAP.put("gui_deposit", "multiconomy.gui.deposit");
        PERMISSION_MAP.put("gui_withdraw", "multiconomy.gui.withdraw");
        PERMISSION_MAP.put("gui_exchange", "multiconomy.gui.exchange");
        PERMISSION_MAP.put("gui_create", "multiconomy.gui.create");
        PERMISSION_MAP.put("gui_edit", "multiconomy.gui.edit");
        PERMISSION_MAP.put("gui_delete", "multiconomy.gui.delete");
        PERMISSION_MAP.put("gui_modify", "multiconomy.gui.modify");
    }

    private GlobalStrings() {
        throw new InternalError("(GlobalStrings) This should not be possible!");
    }

}
