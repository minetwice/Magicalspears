package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpearsGUICommand implements CommandExecutor {
    
    private final MagicalSpears plugin;
    private final SpearManager spearManager;
    
    public SpearsGUICommand(MagicalSpears plugin, SpearManager spearManager) {
        this.plugin = plugin;
        this.spearManager = spearManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("magicalspears.gui")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        Inventory gui = Bukkit.createInventory(null, 9, "§6§lMagical Spears");
        
        int slot = 0;
        for (ItemStack spear : spearManager.getAllSpears()) {
            gui.setItem(slot++, spear);
        }
        
        player.openInventory(gui);
        player.sendMessage("§aOpened Magical Spears GUI!");
        
        return true;
    }
}
