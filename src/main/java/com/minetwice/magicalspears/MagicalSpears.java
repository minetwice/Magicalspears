package com.minetwice.magicalspears;

import org.bukkit.plugin.java.JavaPlugin;
import com.minetwice.magicalspears.managers.*;

public final class MagicalSpears extends JavaPlugin {

    private static MagicalSpears instance;

    private CooldownManager cooldownManager;
    private BossbarManager bossbarManager;
    private SpearManager spearManager;

    private boolean graceActive = false; // for grace period system

    @Override
    public void onEnable() {
        instance = this;

        cooldownManager = new CooldownManager();
        bossbarManager = new BossbarManager();
        spearManager = new SpearManager();

        getLogger().info("✅ MagicalSpears enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("❌ MagicalSpears disabled.");
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

    // --- Grace period system ---
    public boolean isGraceActive() {
        return graceActive;
    }

    public void setGraceActive(boolean active) {
        this.graceActive = active;
    }
}
