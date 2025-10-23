package com.minetwice.magicalspears.managers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

import java.util.*;

public class SpearManager {

    public enum SpearType { INFERNO, FROST, STORM, SOUL, WIND, TIDAL }

    private final Plugin plugin;
    private final NamespacedKey key;

    public SpearManager(Plugin plugin) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, "magical_spear");
    }

    public ItemStack getSpear(SpearType type) {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        switch (type) {
            case INFERNO:
                meta.setDisplayName(ChatColor.RED + "Inferno Spear");
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Engulf foes in blazing flames."));
                break;
            case FROST:
                meta.setDisplayName(ChatColor.AQUA + "Frost Spear");
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Freeze your enemies."));
                break;
            case STORM:
                meta.setDisplayName(ChatColor.DARK_AQUA + "Storm Spear");
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Summon the power of storms."));
                break;
            case SOUL:
                meta.setDisplayName(ChatColor.DARK_PURPLE + "Soul Spear");
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Drain their soul."));
                break;
            case WIND:
                meta.setDisplayName(ChatColor.WHITE + "Wind Spear");
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Push enemies away."));
                break;
            case TIDAL:
                meta.setDisplayName(ChatColor.BLUE + "Tidal Spear");
                meta.setLore(Arrays.asList(ChatColor.GRAY + "Drown them in tidal force."));
                break;
        }

        // mark with persistent data
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, type.name());
        item.setItemMeta(meta);
        return item;
    }

    public Optional<SpearType> getSpearType(ItemStack item) {
        if (item == null || item.getType() != Material.DIAMOND_SWORD || !item.hasItemMeta()) return Optional.empty();
        String val = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (val == null) return Optional.empty();
        try {
            return Optional.of(SpearType.valueOf(val));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public List<ItemStack> getAllSpears() {
        List<ItemStack> list = new ArrayList<>();
        for (SpearType t : SpearType.values()) list.add(getSpear(t));
        return list;
    }
}

