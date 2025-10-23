package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpearsGUICommand implements CommandExecutor {

    private final MagicalSpears plugin;

    public SpearsGUICommand(MagicalSpears plugin) {
        this.plugin = plugin;
    }

    // /spears
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Component.text("Only players can use this."));
            return true;
        }
        Player p = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 9, Component.text("Magical Spears").toString());
        for (ItemStack s : plugin.getSpearManager().getAllSpears()) inv.addItem(s);
        p.openInventory(inv);
        return true;
    }
}

