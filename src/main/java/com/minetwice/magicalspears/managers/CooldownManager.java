package com.minetwice.magicalspears.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(Player player, String spearType) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) {
            return false;
        }
        
        Map<String, Long> playerCooldowns = cooldowns.get(uuid);
        if (!playerCooldowns.containsKey(spearType)) {
            return false;
        }
        
        long cooldownEnd = playerCooldowns.get(spearType);
        return System.currentTimeMillis() < cooldownEnd;
    }

    public void setCooldown(Player player, String spearType, int seconds) {
        UUID uuid = player.getUniqueId();
        cooldowns.putIfAbsent(uuid, new HashMap<>());
        
        long cooldownEnd = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.get(uuid).put(spearType, cooldownEnd);
    }

    public int getRemainingCooldown(Player player, String spearType) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid) || !cooldowns.get(uuid).containsKey(spearType)) {
            return 0;
        }
        
        long cooldownEnd = cooldowns.get(uuid).get(spearType);
        long remaining = (cooldownEnd - System.currentTimeMillis()) / 1000;
        return (int) Math.max(0, remaining);
    }

    public void sendCooldownMessage(Player player, String spearType) {
        int remaining = getRemainingCooldown(player, spearType);
        if (remaining > 0) {
            player.sendActionBar("§cCooldown: " + remaining + "s");
        }
    }

    public void clearCooldowns(Player player) {
        cooldowns.remove(player.getUniqueId());
    }

    public void clearAllCooldowns() {
        cooldowns.clear();
    }
}
