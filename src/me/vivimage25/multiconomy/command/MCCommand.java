package me.vivimage25.multiconomy.command;

import java.util.List;
import me.vivimage25.multiconomy.Multiconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class MCCommand implements TabExecutor {

    private final Multiconomy plugin;

    public MCCommand() {
        plugin = Multiconomy.getInstance();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals("multiconomy")) {
            return true;
        }
        if (args.length == 0) {
            // Show Mutliconomy GUI
            return true;
        }
        String sub_command = args[0].toLowerCase();
        switch (sub_command) {
            case "balance":
            case "bal":
                break;
            case "balancetop":
            case "baltop":
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of();
    }

}
