package me.vivimage25.multiconomy.listener;

import java.util.List;
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

    public static void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 27, INVENTORY_TITLE);
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
            inventory.setItem(9, item);
        }

        // Trade
        if (player.hasPermission(PERMISSION_MAP.get("gui_trade"))) {
            meta.setDisplayName("Trade");
            meta.setLore(List.of("Send a trade request to another player", "Note: other player must accept"));
            item.setType(Material.MAP);
            item.setItemMeta(meta);
            inventory.setItem(11, item);
        }

        if (player.hasPermission(PERMISSION_MAP.get("gui_deposit"))) {
            meta.setDisplayName("Deposit");
            meta.setLore(List.of("Deposit currency to bank"));
            item.setType(Material.CHEST);
            item.setItemMeta(meta);
            inventory.setItem(13, item);
        }

        if (player.hasPermission(PERMISSION_MAP.get("gui_withdraw"))) {
            meta.setDisplayName("Withdraw");
            meta.setLore(List.of("Withdraw currency from bank"));
            item.setType(Material.ENDER_CHEST);
            item.setItemMeta(meta);
            inventory.setItem(15, item);
        }

        if (player.hasPermission(PERMISSION_MAP.get("gui_exchange"))) {
            meta.setDisplayName("Exchange");
            meta.setLore(List.of("Exchange currency for currency"));
            item.setType(Material.HOPPER);
            item.setItemMeta(meta);
            inventory.setItem(17, item);
        }

        if (player.hasPermission(PERMISSION_MAP.get("gui_access"))) {
            player.openInventory(inventory);
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission for that");
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(INVENTORY_TITLE)) {
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
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
    }

}
