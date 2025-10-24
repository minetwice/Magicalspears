package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import com.minetwice.magicalspears.managers.SpearManager.SpearType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSpearCommand implements CommandExecutor {
    
    private final MagicalSpears plugin;
    private final SpearManager spearManager;
    
    public GiveSpearCommand(MagicalSpears plugin, SpearManager spearManager) {
        this.plugin = plugin;
        this.spearManager = spearManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("magicalspears.give")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /givespear <player> <type>");
            sender.sendMessage("§eTypes: FIRE, ICE, LIGHTNING, POISON, KNOCKBACK, WITHER");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found!");
            return true;
        }
        
        SpearType type;
        try {
            type = SpearType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("§cInvalid spear type! Valid types: FIRE, ICE, LIGHTNING, POISON, KNOCKBACK, WITHER");
            return true;
        }
        
        target.getInventory().addItem(spearManager.getSpear(type));
        target.sendMessage("§aYou received a " + type.name() + " spear!");
        sender.sendMessage("§aGave " + target.getName() + " a " + type.name() + " spear!");
        
        return true;
    }
}
