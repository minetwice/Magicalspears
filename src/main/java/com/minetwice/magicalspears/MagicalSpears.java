package com.minetwice.magicalspears;

import org.bukkit.plugin.java.JavaPlugin;

import com.minetwice.magicalspears.managers.CooldownManager;
import com.minetwice.magicalspears.managers.BossbarManager;
import com.minetwice.magicalspears.managers.SpearManager;

public final class MagicalSpears extends JavaPlugin {

    private static MagicalSpears instance;
    private CooldownManager cooldownManager;
    private BossbarManager bossbarManager;
    private SpearManager spearManager;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("MagicalSpears plugin is starting...");

        // Initialize managers
        cooldownManager = new CooldownManager();
        bossbarManager = new BossbarManager();
        spearManager = new SpearManager();

        getLogger().info("All managers initialized successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MagicalSpears plugin has been disabled!");
    }

    public static MagicalSpears getInstance() {
        return instance;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public BossbarManager getBossbarManager() {
        return bossbarManager;
    }

    public SpearManager getSpearManager() {
        return spearManager;
    }
}
