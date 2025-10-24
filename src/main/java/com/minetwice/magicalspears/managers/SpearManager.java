package com.minetwice.magicalspears.managers;

import com.minetwice.magicalspears.MagicalSpears;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Optional;

public class SpearManager {
    
    private final MagicalSpears plugin;
    private final NamespacedKey spearTypeKey;
    
    public enum SpearType {
        FIRE, ICE, LIGHTNING, POISON, KNOCKBACK, WITHER
    }
    
    public SpearManager(MagicalSpears plugin) {
        this.plugin = plugin;
        this.spearTypeKey = new NamespacedKey(plugin, "spear_type");
    }
    
    public ItemStack getSpear(SpearType type) {
        ItemStack spear = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = spear.getItemMeta();
        
        if (meta != null) {
            // Set name and lore based on type
            switch (type) {
                case FIRE:
                    meta.setDisplayName("§6§lFire Spear");
                    meta.setLore(Arrays.asList("§7Sets enemies on fire", "§7upon impact"));
                    meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                    break;
                case ICE:
                    meta.setDisplayName("§b§lIce Spear");
                    meta.setLore(Arrays.asList("§7Freezes enemies", "§7and slows them down"));
                    meta.addEnchant(Enchantment.SHARPNESS, 3, true);
                    break;
                case LIGHTNING:
                    meta.setDisplayName("§e§lLightning Spear");
                    meta.setLore(Arrays.asList("§7Strikes lightning", "§7on impact"));
                    meta.addEnchant(Enchantment.SHARPNESS, 4, true);
                    break;
                case POISON:
                    meta.setDisplayName("§2§lPoison Spear");
                    meta.setLore(Arrays.asList("§7Poisons enemies", "§7on impact"));
                    meta.addEnchant(Enchantment.BANE_OF_ARTHROPODS, 3, true);
                    break;
                case KNOCKBACK:
                    meta.setDisplayName("§f§lKnockback Spear");
                    meta.setLore(Arrays.asList("§7Launches enemies", "§7into the air"));
                    meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
                    break;
                case WITHER:
                    meta.setDisplayName("§8§lWither Spear");
                    meta.setLore(Arrays.asList("§7Applies wither effect", "§7to enemies"));
                    meta.addEnchant(Enchantment.SHARPNESS, 3, true);
                    break;
            }
            
            // Add unbreaking
            meta.addEnchant(Enchantment.UNBREAKING, 3, true);
            
            // Store spear type in persistent data
            meta.getPersistentDataContainer().set(spearTypeKey, PersistentDataType.STRING, type.name());
            
            spear.setItemMeta(meta);
        }
        
        return spear;
    }
    
    public Optional<SpearType> getSpearType(ItemStack item) {
        if (item == null || item.getType() != Material.IRON_SWORD) {
            return Optional.empty();
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return Optional.empty();
        }
        
        String typeString = meta.getPersistentDataContainer().get(spearTypeKey, PersistentDataType.STRING);
        if (typeString == null) {
            return Optional.empty();
        }
        
        try {
            return Optional.of(SpearType.valueOf(typeString));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
