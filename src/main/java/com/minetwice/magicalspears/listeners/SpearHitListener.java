package com.minetwice.magicalspears.listeners;

import com.minetwice.magicalspears.MagicalSpears;
import com.minetwice.magicalspears.managers.SpearManager;
import com.minetwice.magicalspears.managers.CooldownManager;
import com.minetwice.magicalspears.managers.BossbarManager;
import com.minetwice.magicalspears.utils.ParticleUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;
import java.util.UUID;

public class SpearHitListener implements Listener {

    private final MagicalSpears plugin;
    private final SpearManager spearManager;
    private final CooldownManager cooldownManager;
    private final BossbarManager bossbarManager;

    public SpearHitListener(MagicalSpears plugin) {
        this.plugin = plugin;
        this.spearManager = plugin.getSpearManager();
        this.cooldownManager = plugin.getCooldownManager();
        this.bossbarManager = plugin.getBossbarManager();
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player damager = (Player) e.getDamager();
        if (plugin.isGraceActive()) {
            damager.sendMessage(Component.text(ChatColor.YELLOW + "Grace period active — PvP disabled."));
            e.setCancelled(true);
            return;
        }

        ItemStack hand = damager.getInventory().getItemInMainHand();
        Optional<SpearManager.SpearType> typeOpt = spearManager.getSpearType(hand);
        if (!typeOpt.isPresent()) return;

        // Cancel default extra effects? We allow damage but add ability
        SpearManager.SpearType type = typeOpt.get();
        UUID damagerId = damager.getUniqueId();
        String cdKey = "spear_" + type.name();

        if (cooldownManager.isOnCooldown(damagerId, cdKey)) {
            long rem = cooldownManager.getRemaining(damagerId, cdKey);
            damager.sendActionBar(Component.text("Ability on cooldown: " + ((rem + 999)/1000) + "s"));
            e.setCancelled(false); // allow normal hit but no ability
            return;
        }

        // apply ability
        LivingEntity target = (LivingEntity) e.getEntity();
        Location loc = target.getLocation();

        switch (type) {
            case INFERNO:
                // fire ticks + small explosion particle/sound
                target.setFireTicks(5 * 20);
                ParticleUtils.spawnInferno(loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1f, 1f);
                break;
            case FROST:
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5*20, 2));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5*20, 1));
                ParticleUtils.spawnFrost(loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1f, 1f);
                break;
            case STORM:
                // lightning effect (no damage from strikeLightningEffect)
                loc.getWorld().strikeLightningEffect(loc);
                ParticleUtils.spawnStorm(loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f);
                break;
            case SOUL:
                target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 1));
                ParticleUtils.spawnSoul(loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_WITHER_SHOOT, 1f, 1f);
                break;
            case WIND:
                // knockback backwards relative to damager
                Vector dir = target.getLocation().toVector().subtract(damager.getLocation().toVector()).normalize();
                target.setVelocity(dir.multiply(2.5).setY(0.8));
                ParticleUtils.spawnWind(loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_PHANTOM_FLAP, 1f, 1f);
                break;
            case TIDAL:
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 6*20, 1));
                ParticleUtils.spawnTidal(loc);
                loc.getWorld().playSound(loc, Sound.ENTITY_DOLPHIN_SPLASH, 1f, 1f);
                break;
        }

        // set cooldown (10s for all; you can customize per spear)
        cooldownManager.setCooldown(damagerId, cdKey, 10 * 1000L);
        cooldownManager.sendCooldownActionbar(damager, cdKey);

        // reveal coordinates of target to all players for 5 minutes (300s)
        bossbarManager.revealCoordsToAll(target.getUniqueId(), loc, 300);

        // (Optional) create advancement trigger here using Bukkit.Advancement APIs or simply give an achievement-like message
        damager.sendMessage(Component.text(ChatColor.GREEN + "You used " + type.name() + "! Coordinates revealed for 5 minutes."));

        // allow normal damage to continue
    }

    // GUI click handling: show description on click
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle() == null) return;
        if (!e.getView().getTitle().startsWith("Magical Spears")) return;
        e.setCancelled(true);
        ItemStack clicked = e.getCurrentItem();
        if (clicked == null) return;
        e.getWhoClicked().sendMessage(Component.text(ChatColor.YELLOW + "Spear: " + ChatColor.stripColor(clicked.getItemMeta().getDisplayName())));
        e.getWhoClicked().closeInventory();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        // cleanup if needed
    }

    // Prevent damage when grace is active (for all damage types)
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!plugin.isGraceActive()) return;
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            e.setCancelled(true);
        }
    }
}

