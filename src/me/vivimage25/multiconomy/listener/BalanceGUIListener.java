/*
 * Copyright (C) 2022 ViviMage25 <spot.me.69@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.vivimage25.multiconomy.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.vivimage25.multiconomy.economy.EconomyManager;
import me.vivimage25.multiconomy.economy.account.Account;
import me.vivimage25.multiconomy.economy.account.AccountManager;
import me.vivimage25.multiconomy.economy.account.AccountType;
import static me.vivimage25.multiconomy.util.PermissionStrings.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author ViviMage25 <spot.me.69@gmail.com>
 */
public class BalanceGUIListener implements Listener {

    private static final String INVENTORY_TITLE = "Multiconomy Balance Menu";
    private static final Map<UUID, Inventory> INVENTORY_MAP = new HashMap<>();
    private static final AccountManager account_manager = EconomyManager.ACCOUNT_MANAGER;

    public static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 36, INVENTORY_TITLE);
        ItemStack item = new ItemStack(Material.STONE, 1);
        ItemMeta meta = item.getItemMeta();
        
        // Iterate through all accounts with UUID in name.
        List<Account> player_accounts = new ArrayList<>();
        for(Account account : account_manager.getAccounts()) {
            if(account.getName().equals(player.getUniqueId().toString())) {
                player_accounts.add(account);
            }
        }
        
        // Create GUI Menu
        for(Account account : player_accounts) {
            meta.setDisplayName(account.getCurrency().getName());
            meta.setLore(List.of(String.format("Balance: %f", account.getBalance())));
            // Set Material based on currency type
            item.setItemMeta(meta);
            inventory.setItem(1, item);
        }

        // Check for player gui access permission, add inventory to map, open inventory.
        if (player.hasPermission(PERMISSION_GUI_ACCESS)) {
            INVENTORY_MAP.put(player.getUniqueId(), inventory);
            player.openInventory(inventory);
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission for that");
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!event.getInventory().equals(INVENTORY_MAP.get(event.getWhoClicked().getUniqueId()))) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        player.closeInventory();
        if (event.getCurrentItem() == null) {
            return;
        }
        switch(event.getRawSlot()) {
            case 1:
                break;
        }
    }
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if(!event.getInventory().equals(INVENTORY_MAP.get(event.getWhoClicked().getUniqueId()))) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(INVENTORY_MAP.containsKey(event.getPlayer().getUniqueId())) {
            INVENTORY_MAP.remove(event.getPlayer().getUniqueId());
        }
    }
    
}
