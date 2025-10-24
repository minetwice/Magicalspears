package com.minetwice.magicalspears.managers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossbarManager {
    private final Map<UUID, BossBar> cooldownBars = new HashMap<>();

    public void showCooldownBar(Player player, String spearType, int cooldownSeconds) {
        UUID uuid = player.getUniqueId();
        
        removeBossbar(player);
        
        String title = spearType + " Cooldown";
        BossBar bossBar = Bukkit.createBossBar(
            title,
            getBarColor(spearType),
            BarStyle.SOLID
        );
        
        bossBar.addPlayer(player);
        bossBar.setProgress(1.0);
        cooldownBars.put(uuid, bossBar);
        
        startCooldownCountdown(player, bossBar, cooldownSeconds);
    }

    public void showEffectBar(Player player, String effectName, int durationSeconds) {
        UUID uuid = player.getUniqueId();
        
        removeBossbar(player);
        
        BossBar bossBar = Bukkit.createBossBar(
            effectName,
            BarColor.PURPLE,
            BarStyle.SOLID
        );
        
        bossBar.addPlayer(player);
        bossBar.setProgress(1.0);
        cooldownBars.put(uuid, bossBar);
        
        startCooldownCountdown(player, bossBar, durationSeconds);
    }

    private void startCooldownCountdown(Player player, BossBar bossBar, int seconds) {
        Bukkit.getScheduler().runTaskTimer(
            com.minetwice.magicalspears.MagicalSpears.getInstance(),
            new Runnable() {
                int remaining = seconds * 20;
                
                @Override
                public void run() {
                    if (!player.isOnline() || remaining <= 0) {
                        removeBossbar(player);
                        return;
                    }
                    
                    double progress = (double) remaining / (seconds * 20);
                    bossBar.setProgress(Math.max(0, Math.min(1, progress)));
                    
                    remaining -= 2;
                }
            },
            0L,
            2L
        );
    }

    public void removeBossbar(Player player) {
        UUID uuid = player.getUniqueId();
        if (cooldownBars.containsKey(uuid)) {
            BossBar bar = cooldownBars.get(uuid);
            bar.removePlayer(player);
            bar.removeAll();
            cooldownBars.remove(uuid);
        }
    }

    public void removeAllBossbars() {
        cooldownBars.values().forEach(BossBar::removeAll);
        cooldownBars.clear();
    }

    private BarColor getBarColor(String spearType) {
        return switch (spearType.toLowerCase()) {
            case "inferno" -> BarColor.RED;
            case "frost" -> BarColor.BLUE;
            case "storm" -> BarColor.YELLOW;
            case "soul" -> BarColor.PURPLE;
            case "wind" -> BarColor.WHITE;
            case "tidal" -> BarColor.BLUE;
            default -> BarColor.WHITE;
        };
    }
}
