package com.minetwice.magicalspears.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticleUtils {

    public static void spawnInferno(Location loc) {
        World w = loc.getWorld();
        w.spawnParticle(Particle.FLAME, loc, 20, 0.3, 0.3, 0.3, 0.02);
        w.spawnParticle(Particle.LAVA, loc, 10, 0.2, 0.2, 0.2, 0.01);
    }

    public static void spawnFrost(Location loc) {
        World w = loc.getWorld();
        w.spawnParticle(Particle.SNOWFLAKE, loc, 25, 0.4, 0.4, 0.4, 0.01);
        w.spawnParticle(Particle.CLOUD, loc, 10, 0.2, 0.2, 0.2, 0.01);
    }

    public static void spawnStorm(Location loc) {
        World w = loc.getWorld();
        w.spawnParticle(Particle.ELECTRIC_SPARK, loc, 30, 0.4, 0.4, 0.4, 0.03);
    }

    public static void spawnSoul(Location loc) {
        World w = loc.getWorld();
        w.spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 25, 0.3, 0.3, 0.3, 0.01);
    }

    public static void spawnWind(Location loc) {
        World w = loc.getWorld();
        w.spawnParticle(Particle.CLOUD, loc, 20, 0.5, 0.2, 0.5, 0.02);
    }

    public static void spawnTidal(Location loc) {
        World w = loc.getWorld();
        w.spawnParticle(Particle.WATER_SPLASH, loc, 20, 0.4, 0.4, 0.4, 0.02);
        w.spawnParticle(Particle.BUBBLE_COLUMN_UP, loc, 15, 0.2, 0.2, 0.2, 0.01);
    }
}

