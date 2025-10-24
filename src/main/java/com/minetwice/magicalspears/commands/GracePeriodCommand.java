package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GracePeriodCommand implements CommandExecutor {
    
    private final MagicalSpears plugin;
    
    public GracePeriodCommand(MagicalSpears plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("magicalspears.graceperiod")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§eGrace period is currently: §" + (plugin.isGraceActive() ? "aENABLED" : "cDISABLED"));
            return true;
        }
        
        if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable")) {
            plugin.setGraceActive(true);
            sender.sendMessage("§aGrace period has been enabled! Spears are now disabled.");
            return true;
        } else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable")) {
            plugin.setGraceActive(false);
            sender.sendMessage("§cGrace period has been disabled! Spears are now active.");
            return true;
        } else {
            sender.sendMessage("§cUsage: /graceperiod <on|off>");
            return true;
        }
    }
}
