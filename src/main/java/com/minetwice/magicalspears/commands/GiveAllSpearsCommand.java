package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllSpearsCommand implements CommandExecutor {
    
    private final MagicalSpears plugin;
    private final SpearManager spearManager;
    
    public GiveAllSpearsCommand(MagicalSpears plugin, SpearManager spearManager) {
        this.plugin = plugin;
        this.spearManager = spearManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("magicalspears.giveall")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        Player target;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must specify a player from console!");
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found!");
                return true;
            }
        }
        
        for (ItemStack spear : spearManager.getAllSpears()) {
            target.getInventory().addItem(spear);
        }
        
        target.sendMessage("§aYou received all magical spears!");
        if (sender != target) {
            sender.sendMessage("§aGave " + target.getName() + " all magical spears!");
        }
        
        return true;
    }
}
