package com.minetwice.magicalspears.managers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class SpearManager {

    public ItemStack createPoisonSpear() {
        ItemStack spear = new ItemStack(Material.TRIDENT);
        var meta = spear.getItemMeta();
        meta.setDisplayName("§aPoison Spear");
        spear.setItemMeta(meta);
        return spear;
    }

    public void givePoisonSpear(Player player) {
        player.getInventory().addItem(createPoisonSpear());
    }
}
