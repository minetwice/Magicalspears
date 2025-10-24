package com.minetwice.magicalspears.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public class SpearManager {
    private final NamespacedKey spearTypeKey;

    public SpearManager() {
        this.spearTypeKey = new NamespacedKey(
            com.minetwice.magicalspears.MagicalSpears.getInstance(),
            "spear_type"
        );
    }

    public ItemStack createSpear(String type) {
        ItemStack spear = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = spear.getItemMeta();

        if (meta == null) return spear;

        // Set spear type in persistent data
        meta.getPersistentDataContainer().set(
            spearTypeKey,
            PersistentDataType.STRING,
            type.toLowerCase()
        );

        // Configure based on type
        switch (type.toLowerCase()) {
            case "inferno":
                meta.displayName(Component.text("Inferno Spear", NamedTextColor.RED, TextDecoration.BOLD));
                meta.lore(createLore(
                    "Burns enemies on hit",
                    "Cooldown: 30s",
                    NamedTextColor.GOLD
                ));
                meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                break;

            case "frost":
                meta.displayName(Component.text("Frost Spear", NamedTextColor.AQUA, TextDecoration.BOLD));
                meta.lore(createLore(
                    "Freezes enemies on hit",
                    "Cooldown: 25s",
                    NamedTextColor.AQUA
                ));
                break;

            case "storm":
                meta.displayName(Component.text("Storm Spear", NamedTextColor.YELLOW, TextDecoration.BOLD));
                meta.lore(createLore(
                    "Strikes lightning on hit",
                    "Cooldown: 35s",
                    NamedTextColor.YELLOW
                ));
                break;

            case "soul":
                meta.displayName(Component.text("Soul Spear", NamedTextColor.DARK_PURPLE, TextDecoration.BOLD));
                meta.lore(createLore(
                    "Drains life from enemies",
                    "Cooldown: 40s",
                    NamedTextColor.LIGHT_PURPLE
                ));
                break;

            case "wind":
                meta.displayName(Component.text("Wind Spear", NamedTextColor.WHITE, TextDecoration.BOLD));
                meta.lore(createLore(
                    "Knocks back enemies",
                    "Cooldown: 20s",
                    NamedTextColor.GRAY
                ));
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                break;

            case "tidal":
                meta.displayName(Component.text("Tidal Spear", NamedTextColor.BLUE, TextDecoration.BOLD));
                meta.lore(createLore(
                    "Creates water effects",
                    "Cooldown: 30s",
                    NamedTextColor.DARK_AQUA
                ));
                break;
        }

        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        spear.setItemMeta(meta);

        return spear;
    }

    private List<Component> createLore(String ability, String cooldown, NamedTextColor color) {
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        lore.add(Component.text(ability, color));
        lore.add(Component.text(cooldown, NamedTextColor.GRAY));
        lore.add(Component.text(""));
        lore.add(Component.text("Magical Spear", NamedTextColor.DARK_GRAY, TextDecoration.ITALIC));
        return lore;
    }

    public String getSpearType(ItemStack item) {
        if (item == null || item.getType() != Material.NETHERITE_SWORD) {
            return null;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        return meta.getPersistentDataContainer().get(spearTypeKey, PersistentDataType.STRING);
    }

    public boolean isSpear(ItemStack item) {
        return getSpearType(item) != null;
    }

    public int getCooldown(String spearType) {
        return switch (spearType.toLowerCase()) {
            case "wind" -> 20;
            case "frost" -> 25;
            case "inferno", "tidal" -> 30;
            case "storm" -> 35;
            case "soul" -> 40;
            default -> 30;
        };
    }
}
