/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speaj.minecraft.petwolf;


import java.util.logging.Logger;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author lane
 */
public class PetWolf extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("com.speaj.minecraft.PetWolf");

    @Override
        public void onEnable() {
    }

    @Override
        public void onDisable() {
    }

    @Override
    public boolean onCommand(
        CommandSender sender,
        Command command,
        String label,
        String[] arguments
    ) {
        boolean processed = false;

        if (label.equalsIgnoreCase("petwolf")) {
            if (sender instanceof Player) {
                Player me = (Player) sender;
                Location spot = me.getLocation();
                World world = me.getWorld();
                
                Wolf wolf = world.spawn(spot, Wolf.class);
                wolf.setCollarColor(DyeColor.PINK);
                LOG.info("[PetWolf] Howl!");
                
                processed = true;
            }
            
        }

        return processed;
    }
}