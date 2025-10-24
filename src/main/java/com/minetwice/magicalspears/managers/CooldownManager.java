package com.minetwice.magicalspears.managers;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID playerId) {
        if (!cooldowns.containsKey(playerId)) return false;
        return System.currentTimeMillis() < cooldowns.get(playerId);
    }

    public void setCooldown(UUID playerId, long milliseconds) {
        cooldowns.put(playerId, System.currentTimeMillis() + milliseconds);
    }

    public long getRemaining(UUID playerId) {
        if (!cooldowns.containsKey(playerId)) return 0;
        return Math.max(0, cooldowns.get(playerId) - System.currentTimeMillis());
    }

    public void clearCooldown(UUID playerId) {
        cooldowns.remove(playerId);
    }
}
