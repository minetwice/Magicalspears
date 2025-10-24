package com.minetwice.magicalspears.advancements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdvancementManager {

    private final Plugin plugin;

    public AdvancementManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void grantAdvancement(Player player, String id) {
        NamespacedKey key = new NamespacedKey(plugin, id);
        Advancement adv = Bukkit.getAdvancement(key);

        if (adv == null) {
            plugin.getLogger().warning("Advancement not found: " + id);
            return;
        }

        AdvancementProgress progress = player.getAdvancementProgress(adv);
        for (String criteria : progress.getRemainingCriteria()) {
            progress.awardCriteria(criteria);
        }
    }
}
