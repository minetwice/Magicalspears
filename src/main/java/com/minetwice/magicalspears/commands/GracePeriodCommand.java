package com.minetwice.magicalspears.commands;

import com.minetwice.magicalspears.MagicalSpears;
import net.kyori.adventure.text.Component;
import org.bukkit.command.*;

public class GracePeriodCommand implements CommandExecutor {

    private final MagicalSpears plugin;

    public GracePeriodCommand(MagicalSpears plugin) {
        this.plugin = plugin;
    }

    // /grace -> toggles start 15 minutes
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.isGraceActive()) {
            sender.sendMessage(Component.text("Grace is already active."));
            return true;
        }
        plugin.setGraceActive(true);
        plugin.getServer().broadcast(Component.text("Grace period started: 15 minutes. PvP is disabled."));
        // schedule end
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            plugin.setGraceActive(false);
            plugin.getServer().broadcast(Component.text("Grace period ended. PvP is enabled."));
        }, 15 * 60 * 20L);
        return true;
    }
}

