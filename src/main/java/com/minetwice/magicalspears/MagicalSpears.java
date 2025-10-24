package com.minetwice.magicalspears;

import com.minetwice.magicalspears.commands.GiveSpearCommand;
import com.minetwice.magicalspears.commands.GracePeriodCommand;
import com.minetwice.magicalspears.commands.GiveAllSpearsCommand;
import com.minetwice.magicalspears.commands.SpearsGUICommand;
import com.minetwice.magicalspears.listeners.SpearHitListener;
import com.minetwice.magicalspears.managers.SpearManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicalSpears extends JavaPlugin {
    
    private SpearManager spearManager;
    private boolean graceActive = false;
    
    @Override
    public void onEnable() {
        // Initialize managers
        this.spearManager = new SpearManager(this);
        
        // Register commands
        getCommand("givespear").setExecutor(new GiveSpearCommand(this, spearManager));
        getCommand("graceperiod").setExecutor(new GracePeriodCommand(this));
        getCommand("giveallspears").setExecutor(new GiveAllSpearsCommand(this, spearManager));
        getCommand("spearsgui").setExecutor(new SpearsGUICommand(this, spearManager));
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new SpearHitListener(this, spearManager), this);
        
        // Save default config
        saveDefaultConfig();
        
        getLogger().info("MagicalSpears has been enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("MagicalSpears has been disabled!");
    }
    
    public SpearManager getSpearManager() {
        return spearManager;
    }
    
    public boolean isGraceActive() {
        return graceActive;
    }
    
    public void setGraceActive(boolean active) {
        this.graceActive = active;
    }
}
