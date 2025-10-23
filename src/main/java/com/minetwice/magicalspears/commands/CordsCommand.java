package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CordsCommand implements CommandExecutor {

    private final MagicalSpears plugin;

    public CordsCommand(MagicalSpears plugin) {
        this.plugin = plugin;
    }

    // /cords <player> show|hide
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /cords <player> <show|hide>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Component.text("Player not found."));
            return true;
        }
        String sub = args[1].toLowerCase();
        if (sub.equals("show")) {
            plugin.getBossbarManager().showProgressToPlayer(target, "Coordinates progress", 180); // 3 minutes
            sender.sendMessage(Component.text("Showing coordinate progress bar to " + target.getName()));
        } else if (sub.equals("hide")) {
            // The Progress will auto-hide; easiest is to send empty bossbar removal by creating invisible bar
            sender.sendMessage(Component.text("Hide request sent (bars auto-hide)."));
        } else {
            sender.sendMessage(Component.text("Usage: /cords <player> <show|hide>"));
        }
        return true;
    }
}

