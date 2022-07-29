package fr.antiquemc.game.module;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public enum ModulePos {

    LOBBY(Bukkit.getWorld("world"),0, 100, 0, -0.5f, -2.3f),
    //modulePOS1
    MODULE_ONE_POS1(Bukkit.getWorld("world"),15, 100, 15, -0.5f, -2.3f),
    MODULE_ONE_POS2(Bukkit.getWorld("world"),15, 100, 2, -0.5f, -2.3f),
    //modulePOS2
    MODULE_TWO_POS1(Bukkit.getWorld("world"), 1, 100, 13, -0.5f, -2.3f),
    MODULE_TWO_POS2(Bukkit.getWorld("world"), 1, 100, 2, -0.5f, -2.3f),
    //modulePOS3
    MODULE_TREE_POS1(Bukkit.getWorld("world"), -10, 100, 14, -0.5f, -2.3f),
    MODULE_TREE_POS2(Bukkit.getWorld("world"), -10, 100, 2, -0.5f, -2.3f),
    //modulePOS4
    MODULE_FOUR_POS1(Bukkit.getWorld("world"), -11, 100, -4, -0.5f, -2.3f),
    MODULE_FOUR_POS2(Bukkit.getWorld("world"), -11, 100, -14, -0.5f, -2.3f),
    //modulePOS5
    MODULE_FIVE_POS1(Bukkit.getWorld("world"), 1, 100, -4, -0.5f, -2.3f),
    MODULE_FIVE_POS2(Bukkit.getWorld("world"), 1, 100, -14, -0.5f, -2.3f),
    //modulePOS6
    MODULE_SIX_POS1(Bukkit.getWorld("world"), 15, 100, -14, -0.5f, -2.3f),
    MODULE_SIX_POS2(Bukkit.getWorld("world"), 15, 100, -4, -0.5f, -2.3f), ;

    private Location location;

    ModulePos(World world, double v, int i, double v1, float v2, float v3) {
        this.location = new Location(world, v, i, v1, v2, v3);
    }

    public Location getLocation() {
        return location;
    }
}
