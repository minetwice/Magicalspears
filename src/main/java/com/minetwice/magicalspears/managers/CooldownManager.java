package com.minetwice.magicalspears.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    private final HashMap<String, Long> cooldowns = new HashMap<>();

    private String key(UUID id, String ability) {
        return id.toString() + ":" + ability.toLowerCase();
    }

    public boolean isOnCooldown(UUID id, String ability) {
        String k = key(id, ability);
        return cooldowns.containsKey(k) && System.currentTimeMillis() < cooldowns.get(k);
    }

    public void setCooldown(UUID id, String ability, long milliseconds) {
        cooldowns.put(key(id, ability), System.currentTimeMillis() + milliseconds);
    }

    public long getRemaining(UUID id, String ability) {
        String k = key(id, ability);
        if (!cooldowns.containsKey(k)) return 0;
        return Math.max(0, cooldowns.get(k) - System.currentTimeMillis());
    }

    public void sendCooldownActionbar(Player player, String message) {
        player.sendActionBar("§e⏳ " + message);
    }
}
