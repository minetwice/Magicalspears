package com.minetwice.magicalspears.managers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BossbarManager {

    private final HashMap<UUID, BossBar> bars = new HashMap<>();

    public void showBar(Player player, String title, BarColor color, BarStyle style, double progress) {
        BossBar bar = Bukkit.createBossBar(title, color, style);
        bar.setProgress(progress);
        bar.addPlayer(player);
        bars.put(player.getUniqueId(), bar);
    }

    public void updateBar(Player player, double progress, String newTitle) {
        BossBar bar = bars.get(player.getUniqueId());
        if (bar != null) {
            bar.setProgress(progress);
            if (newTitle != null) bar.setTitle(newTitle);
        }
    }

    public void removeBar(Player player) {
        BossBar bar = bars.remove(player.getUniqueId());
        if (bar != null) bar.removeAll();
    }
}
