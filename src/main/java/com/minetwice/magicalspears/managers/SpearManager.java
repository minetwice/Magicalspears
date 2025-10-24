import org.bukkit.util.Vector;
package com.minetwice.magicalspears.managers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SpearManager {

    public enum SpearType {
        POISON,
        FIRE,
        ICE,
        LIGHTNING
    }

    public ItemStack createSpear(SpearType type) {
        ItemStack spear = new ItemStack(Material.TRIDENT);
        ItemMeta meta = spear.getItemMeta();
        meta.setDisplayName("§b" + type.name() + " Spear");
        meta.setLocalizedName(type.name().toLowerCase());
        spear.setItemMeta(meta);
        return spear;
    }

    public SpearType getSpearType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        String name = item.getItemMeta().getLocalizedName();
        if (name == null) return null;
        try {
            return SpearType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<ItemStack> getAllSpears() {
        List<ItemStack> list = new ArrayList<>();
        for (SpearType type : SpearType.values()) {
            list.add(createSpear(type));
        }
        return list;
    }
}
