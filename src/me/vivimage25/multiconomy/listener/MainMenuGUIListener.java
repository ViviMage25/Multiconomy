package me.vivimage25.multiconomy.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static me.vivimage25.multiconomy.util.GlobalStrings.PERMISSION_MAP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenuGUIListener implements Listener {

    private static final String INVENTORY_TITLE = "Multiconomy Main Menu";
    private static final Map<UUID, Inventory> INVENTORY_MAP = new HashMap<>();

    public static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 36, INVENTORY_TITLE);
        ItemStack item = new ItemStack(Material.STONE, 1);
        ItemMeta meta = item.getItemMeta();

        // Balance
        if (player.hasPermission(PERMISSION_MAP.get("gui_balance"))) {
            meta.setDisplayName("Balance");
            meta.setLore(List.of("Check your balance(s)", "Note: this will display all active currencies"));
            item.setType(Material.GOLD_INGOT);
            item.setItemMeta(meta);
            inventory.setItem(1, item);
        }

        // Balance Top
        if (player.hasPermission(PERMISSION_MAP.get("gui_balancetop"))) {
            meta.setDisplayName("Balance Top");
            meta.setLore(List.of("Check the top 10 balances on the server", "Note: this will be split by all active currencies"));
            item.setType(Material.GOLD_ORE);
            item.setItemMeta(meta);
            inventory.setItem(3, item);
        }

        // Worth
        if (player.hasPermission(PERMISSION_MAP.get("gui_worth"))) {
            meta.setDisplayName("Worth");
            meta.setLore(List.of("Check your total worth"));
            item.setType(Material.EMERALD);
            item.setItemMeta(meta);
            inventory.setItem(5, item);
        }

        // Worth Top
        if (player.hasPermission(PERMISSION_MAP.get("gui_worthtop"))) {
            meta.setDisplayName("Worth Top");
            meta.setLore(List.of("Check the top 10 total worth's on the server"));
            item.setType(Material.EMERALD_ORE);
            item.setItemMeta(meta);
            inventory.setItem(7, item);
        }

        // Pay
        if (player.hasPermission(PERMISSION_MAP.get("gui_pay"))) {
            meta.setDisplayName("Pay");
            meta.setLore(List.of("Pay another player"));
            item.setType(Material.PAPER);
            item.setItemMeta(meta);
            inventory.setItem(12, item);
        }

        // Trade
        if (player.hasPermission(PERMISSION_MAP.get("gui_trade"))) {
            meta.setDisplayName("Trade");
            meta.setLore(List.of("Send a trade request to another player", "Note: other player must accept"));
            item.setType(Material.MAP);
            item.setItemMeta(meta);
            inventory.setItem(14, item);
        }

        // Deposit
        if (player.hasPermission(PERMISSION_MAP.get("gui_deposit"))) {
            meta.setDisplayName("Deposit");
            meta.setLore(List.of("Deposit currency to bank"));
            item.setType(Material.CHEST);
            item.setItemMeta(meta);
            inventory.setItem(20, item);
        }

        // Withdraw
        if (player.hasPermission(PERMISSION_MAP.get("gui_withdraw"))) {
            meta.setDisplayName("Withdraw");
            meta.setLore(List.of("Withdraw currency from bank"));
            item.setType(Material.ENDER_CHEST);
            item.setItemMeta(meta);
            inventory.setItem(22, item);
        }

        // Exchange
        if (player.hasPermission(PERMISSION_MAP.get("gui_exchange"))) {
            meta.setDisplayName("Exchange");
            meta.setLore(List.of("Exchange currency for currency"));
            item.setType(Material.HOPPER);
            item.setItemMeta(meta);
            inventory.setItem(24, item);
        }

        // Create (Admin-Only)
        if (player.hasPermission(PERMISSION_MAP.get("gui_create"))) {
            meta.setDisplayName("Create Currency");
            meta.setLore(List.of("Create a new global currency"));
            item.setType(Material.CRAFTING_TABLE);
            item.setItemMeta(meta);
            inventory.setItem(28, item);
        }

        // Edit (Admin-Only)
        if (player.hasPermission(PERMISSION_MAP.get("gui_edit"))) {
            meta.setDisplayName("Edit Currency");
            meta.setLore(List.of("Create a global currency"));
            item.setType(Material.SMITHING_TABLE);
            item.setItemMeta(meta);
            inventory.setItem(30, item);
        }

        // Delete (Admin-Only)
        if (player.hasPermission(PERMISSION_MAP.get("gui_delete"))) {
            meta.setDisplayName("Delete Currency");
            meta.setLore(List.of("Delete a global currency"));
            item.setType(Material.STONECUTTER);
            item.setItemMeta(meta);
            inventory.setItem(32, item);
        }

        // Modify (Admin-Only) - Set,Give,Remove
        if (player.hasPermission(PERMISSION_MAP.get("gui_modify"))) {
            meta.setDisplayName("Modify Player");
            meta.setLore(List.of("Modify a player's currencies"));
            item.setType(Material.ENCHANTING_TABLE);
            item.setItemMeta(meta);
            inventory.setItem(34, item);
        }
        
        // Check for player gui access permission, add inventory to map, open inventory.
        if (player.hasPermission(PERMISSION_MAP.get("gui_access"))) {
            INVENTORY_MAP.put(player.getUniqueId(), inventory);
            player.openInventory(inventory);
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission for that");
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //if (!event.getView().getTitle().equals(INVENTORY_TITLE)) {
        if(!event.getInventory().equals(INVENTORY_MAP.get(event.getWhoClicked().getUniqueId()))) {
            return;
        }
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        player.closeInventory();
        if (event.getCurrentItem() == null) {
            return;
        }
        switch (event.getRawSlot()) {
            case 1: // Balance
                BalanceGUIListener.openInventory(player);
                break;
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        //if (event.getView().getTitle().equals(INVENTORY_TITLE)) {
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
