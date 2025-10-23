package com.minetwice.magicalspears;

import com.minetwice.magicalspears.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicalSpears extends JavaPlugin {

    private CooldownManager cooldownManager;
    private BossbarManager bossbarManager;
    private SpearManager spearManager;
    private boolean graceActive = false;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager(this);
        bossbarManager = new BossbarManager(this);
        spearManager = new SpearManager(this);

        getCommand("givespear").setExecutor(new GiveSpearCommand(this));
        getCommand("reveal").setExecutor(new RevealCommand(this));
        getCommand("gui").setExecutor(new GUICommand(this));
        getCommand("grace").setExecutor(new GracePeriodCommand(this));

        getServer().getPluginManager().registerEvents(spearManager, this);
        getLogger().info("✅ MagicalSpears enabled successfully!");
    }

    public CooldownManager getCooldownManager() { return cooldownManager; }
    public BossbarManager getBossbarManager() { return bossbarManager; }
    public SpearManager getSpearManager() { return spearManager; }

    public boolean isGraceActive() { return graceActive; }
    public void setGraceActive(boolean active) { this.graceActive = active; }
}
