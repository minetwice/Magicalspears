package com.minetwice.magicalspears;

import org.bukkit.plugin.java.JavaPlugin;
import com.minetwice.magicalspears.listeners.SpearHitListener;
import com.minetwice.magicalspears.managers.*;
import com.minetwice.magicalspears.commands.*;

public class MagicalSpears extends JavaPlugin {

    private static MagicalSpears instance;
    private CooldownManager cooldownManager;
    private BossbarManager bossbarManager;
    private SpearManager spearManager;

    @Override
    public void onEnable() {
        instance = this;
        cooldownManager = new CooldownManager();
        bossbarManager = new BossbarManager();
        spearManager = new SpearManager();

        getServer().getPluginManager().registerEvents(new SpearHitListener(this), this);

        getCommand("givespear").setExecutor(new GiveSpearCommand(this));
        getCommand("givespears").setExecutor(new GiveAllSpearsCommand(this));
        getCommand("cords").setExecutor(new CordsCommand(this));
        getCommand("spears").setExecutor(new SpearsGUICommand(this));
        getCommand("grace").setExecutor(new GracePeriodCommand(this));

        getLogger().info("✅ MagicalSpears (1.21.1) Enabled Successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("❌ MagicalSpears Disabled.");
    }

    public static MagicalSpears getInstance() { return instance; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
    public BossbarManager getBossbarManager() { return bossbarManager; }
    public SpearManager getSpearManager() { return spearManager; }
}

