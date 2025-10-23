package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAllSpearsCommand implements CommandExecutor {

    private final MagicalSpears plugin;

    public GiveAllSpearsCommand(MagicalSpears plugin) {
        this.plugin = plugin;
    }

    // usage: /givespears <player>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Component.text("Usage: /givespears <player>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("Player not found."));
            return true;
        }
        for (ItemStack s : plugin.getSpearManager().getAllSpears()) {
            target.getInventory().addItem(s);
        }
        sender.sendMessage(Component.text("Gave all spears to " + target.getName()));
        target.sendMessage(Component.text("You received all magical spears."));
        return true;
    }
}

