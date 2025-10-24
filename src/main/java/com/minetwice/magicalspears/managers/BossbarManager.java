package com.minetwice.magicalspears.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.*;
import org.bukkit.entity.Player;

import java.util.*;

public class BossbarManager {

    private final Map<UUID, BossBar> bars = new HashMap<>();

    public void showProgressToPlayer(Player player, String title, int durationTicks) {
        BossBar bar = Bukkit.createBossBar(title, BarColor.BLUE, BarStyle.SEGMENTED_10);
        bar.addPlayer(player);
        bars.put(player.getUniqueId(), bar);

        Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("MagicalSpears"), task -> {
            double progress = bar.getProgress() - (1.0 / (durationTicks / 20.0));
            if (progress <= 0) {
                bar.removeAll();
                bars.remove(player.getUniqueId());
                task.cancel();
            } else {
                bar.setProgress(progress);
            }
        }, 0L, 20L);
    }

    public void revealCoordsToAll(UUID source, Location loc, int seconds) {
        String msg = "§aPlayer " + source + " is at X=" + loc.getBlockX() + " Y=" + loc.getBlockY() + " Z=" + loc.getBlockZ();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("MagicalSpears"), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("§7[Coords hidden again]");
            }
        }, seconds * 20L);
    }
}
