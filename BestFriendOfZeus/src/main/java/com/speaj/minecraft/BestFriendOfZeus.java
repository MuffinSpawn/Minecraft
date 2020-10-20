/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speaj.minecraft;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author lane
 */
public class BestFriendOfZeus extends JavaPlugin implements Listener {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    Player friend_of_zeus;
    World world;
    Location spot;
    boolean enabled = false;
    
    @Override
    public boolean onCommand(
        CommandSender sender,
        Command command,
        String label,
        String[] arguments
    ) {
        boolean processed = false;
        this.friend_of_zeus = (Player) sender;
        this.world = friend_of_zeus.getWorld();
        this.spot = friend_of_zeus.getLocation();
        
        if (this.command_invoked(sender, label, arguments)) {
            this.toggle_plugin(arguments[0].equals("on"));

            processed = true;
        }
        
        return processed;
    }
    
    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
        manager.registerEvents(this, this);
    }
    
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();
        Player player = this.get_targeted_player(event);

        LOG.log(Level.INFO, "Player {0} targeted by {1}", new Object[]{player, entity});

        if (player == this.friend_of_zeus) {
            world.strikeLightning(location);
        }
    }

    private Player get_targeted_player(EntityTargetEvent event) {
        Entity target = event.getTarget();
        Player player = null;

        if (
            target instanceof Player &&
            event.getReason() == TargetReason.CLOSEST_PLAYER
        ) {
            player = (Player) target;
        }

        return player;
    }

    private boolean command_invoked(
        CommandSender sender,
        String label,
        String[] arguments
    ) {
        return  sender instanceof Player &&
                label.equalsIgnoreCase("zeus") &&
                arguments.length > 0 &&
                (arguments[0].equals("on") || arguments[0].equals("off"));

    }

    private void toggle_plugin(boolean enabled) {
        this.enabled = enabled;
        String modifier = enabled?"":" not ";
        this.friend_of_zeus.sendMessage("Zeus is" + modifier + "yer friend!");
    }
}
