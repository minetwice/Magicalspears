package com.minetwice.magicalspears.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Plugin plugin;
    // player UUID -> spearName -> expireMillis
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    private final long defaultCooldownMillis = 10 * 1000L; // 10 seconds default; can adjust per spear

    public CooldownManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean isOnCooldown(UUID player, String key) {
        Map<String, Long> map = cooldowns.get(player);
        if (map == null) return false;
        Long exp = map.get(key);
        if (exp == null) return false;
        if (System.currentTimeMillis() > exp) {
            map.remove(key);
            return false;
        }
        return true;
    }

    public long getRemaining(UUID player, String key) {
        Map<String, Long> map = cooldowns.get(player);
        if (map == null) return 0;
        Long exp = map.get(key);
        if (exp == null) return 0;
        long rem = exp - System.currentTimeMillis();
        return Math.max(rem, 0);
    }

    public void setCooldown(UUID player, String key, long millis) {
        cooldowns.computeIfAbsent(player, k -> new HashMap<>()).put(key, System.currentTimeMillis() + millis);
    }

    public void setDefaultCooldown(UUID player, String key) {
        setCooldown(player, key, defaultCooldownMillis);
    }

    // helper to show actionbar remaining seconds (uses adventure)
    public void sendCooldownActionbar(Player p, String key) {
        long rem = getRemaining(p.getUniqueId(), key);
        if (rem <= 0) return;
        long seconds = (rem + 999) / 1000;
        p.sendActionBar(Component.text("Cooldown: " + seconds + "s"));
    }

}

