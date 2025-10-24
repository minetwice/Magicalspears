package com.minetwice.magicalspears;

import org.bukkit.plugin.java.JavaPlugin;
import com.minetwice.magicalspears.managers.CooldownManager;
import com.minetwice.magicalspears.managers.BossbarManager;
import com.minetwice.magicalspears.managers.SpearManager;
import com.minetwice.magicalspears.commands.*;
import com.minetwice.magicalspears.listeners.*;

public final class MagicalSpears extends JavaPlugin {

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

        // Register commands
        getCommand("givespear").setExecutor(new GiveSpearCommand(this));
        getCommand("giveallspears").setExecutor(new GiveAllSpearsCommand(this));
        getCommand("cords").setExecutor(new CordsCommand(this));
        getCommand("graceperiod").setExecutor(new GracePeriodCommand(this));
        getCommand("spearui").setExecutor(new SpearsGUICommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new SpearHitListener(this), this);

        getLogger().info("MagicalSpears plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MagicalSpears plugin disabled!");
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
