package com.minetwice.magicalspears.listeners;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import com.minetwice.magicalspears.managers.SpearManager.SpearType;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Optional;

public class SpearHitListener implements Listener {
    
    private final MagicalSpears plugin;
    private final SpearManager spearManager;
    
    public SpearHitListener(MagicalSpears plugin, SpearManager spearManager) {
        this.plugin = plugin;
        this.spearManager = spearManager;
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Check if attacker is a player
        if (!(event.getDamager() instanceof Player)) return;
        
        Player player = (Player) event.getDamager();
        
        // Check grace period
        if (plugin.isGraceActive()) {
            player.sendMessage("§cSpears are disabled during grace period!");
            event.setCancelled(true);
            return;
        }
        
        // Get item in hand
        ItemStack item = player.getInventory().getItemInMainHand();
        Optional<SpearType> spearTypeOpt = spearManager.getSpearType(item);
        
        if (!spearTypeOpt.isPresent()) return;
        
        SpearType spearType = spearTypeOpt.get();
        
        // Check if target is a living entity
        if (!(event.getEntity() instanceof LivingEntity)) return;
        
        LivingEntity target = (LivingEntity) event.getEntity();
        Location location = target.getLocation();
        
        // Apply spear effects
        applySpearEffect(spearType, target, player, location);
    }
    
    private void applySpearEffect(SpearType type, LivingEntity target, Player attacker, Location location) {
        switch (type) {
            case FIRE:
                target.setFireTicks(100);
                location.getWorld().spawnParticle(Particle.FLAME, location, 30, 0.5, 0.5, 0.5, 0.1);
                location.getWorld().playSound(location, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
                attacker.sendMessage("§6Fire Spear activated!");
                break;
                
            case ICE:
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
                target.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 100, 1));
                location.getWorld().spawnParticle(Particle.SNOWFLAKE, location, 30, 0.5, 0.5, 0.5, 0.1);
                location.getWorld().playSound(location, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.5f);
                attacker.sendMessage("§bIce Spear activated!");
                break;
                
            case LIGHTNING:
                location.getWorld().strikeLightning(target.getLocation());
                location.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, location, 50, 0.5, 0.5, 0.5, 0.2);
                attacker.sendMessage("§eLightning Spear activated!");
                break;
                
            case POISON:
                target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 0));
                location.getWorld().spawnParticle(Particle.ITEM_SLIME, location, 30, 0.5, 0.5, 0.5, 0.1);
                location.getWorld().playSound(location, Sound.ENTITY_SPIDER_AMBIENT, 1.0f, 0.8f);
                attacker.sendMessage("§2Poison Spear activated!");
                break;
                
            case KNOCKBACK:
                Vector direction = target.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
                target.setVelocity(direction.multiply(3).setY(1));
                location.getWorld().spawnParticle(Particle.EXPLOSION, location, 10, 0.5, 0.5, 0.5, 0.1);
                location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.2f);
                attacker.sendMessage("§fKnockback Spear activated!");
                break;
                
            case WITHER:
                target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
                location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location, 30, 0.5, 0.5, 0.5, 0.1);
                location.getWorld().playSound(location, Sound.ENTITY_WITHER_SHOOT, 1.0f, 1.0f);
                attacker.sendMessage("§8Wither Spear activated!");
                break;
        }
    }
}
