package com.minetwice.magicalspears.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class BossbarManager {

    private final Plugin plugin;
    // mapping an id → bossbar so we can cancel
    private final Map<UUID, BossBar> activeBars = new HashMap<>();

    public BossbarManager(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Show coordinates of 'target' to all players for durationSeconds
     */
    public void revealCoordsToAll(UUID targetId, Location loc, int durationSeconds) {
        // create bar text
        String title = "LOC - " + loc.getWorld().getName()
                + " X:" + loc.getBlockX()
                + " Y:" + loc.getBlockY()
                + " Z:" + loc.getBlockZ();

        BossBar bar = Bukkit.createBossBar(Component.text(title), BarColor.GREEN, BarStyle.SOLID);
        // add all online players
        for (Player p : Bukkit.getOnlinePlayers()) bar.addPlayer(p);
        UUID id = targetId;
        activeBars.put(id, bar);

        // schedule removal after duration
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            BossBar b = activeBars.remove(id);
            if (b != null) {
                for (Player p : Bukkit.getOnlinePlayers()) b.removePlayer(p);
                b.setVisible(false);
            }
        }, durationSeconds * 20L);
    }

    /**
     * Show custom bar to a player for progressSeconds (used for cords progress)
     */
    public void showProgressToPlayer(Player p, String text, int progressSeconds) {
        BossBar bar = Bukkit.createBossBar(Component.text(text), BarColor.YELLOW, BarStyle.SEGMENTED_10);
        bar.addPlayer(p);

        final int totalTicks = progressSeconds * 20;
        // update percent periodically
        final int taskId = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            int elapsed = 0;
            @Override
            public void run() {
                elapsed += 5;
                double percent = Math.max(0, 1.0 - (elapsed / (double) totalTicks));
                bar.setProgress(percent);
                if (elapsed >= totalTicks) {
                    bar.removePlayer(p);
                    bar.setVisible(false);
                    Bukkit.getScheduler().cancelTask(taskId);
                }
            }
        }, 0L, 5L).getTaskId();
    }

}

