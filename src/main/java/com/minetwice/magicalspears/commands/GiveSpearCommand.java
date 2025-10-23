
package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveSpearCommand implements CommandExecutor {

    private final MagicalSpears plugin;

    public GiveSpearCommand(MagicalSpears plugin) {
        this.plugin = plugin;
    }

    // usage: /givespear <player> <spearname>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /givespear <player> <INFERNO|FROST|STORM|SOUL|WIND|TIDAL>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("Player not found."));
            return true;
        }
        try {
            SpearManager.SpearType type = SpearManager.SpearType.valueOf(args[1].toUpperCase());
            ItemStack spear = plugin.getSpearManager().getSpear(type);
            target.getInventory().addItem(spear);
            sender.sendMessage(Component.text("Gave " + type.name() + " to " + target.getName()));
            target.sendMessage(Component.text("You received " + type.name()));
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(Component.text("Invalid spear name."));
        }
        return true;
    }
}
