package com.ne0nx3r0.lonely;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class LonelyWildernessCommandExecutor implements CommandExecutor {
    private final LonelyWilderness plugin;

    public LonelyWildernessCommandExecutor(LonelyWilderness plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        if(!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED+"/wild cannot be used from console");
        
            return true;
        }
        
        if(!cs.hasPermission("lonely.commands.wild")) {
            cs.sendMessage(ChatColor.RED+"You do not have permission to use this command");
        
            return true;
        }
        
        Player player = (Player) cs;
        
        if(!player.getWorld().getName().equalsIgnoreCase("world161")) {
            cs.sendMessage(ChatColor.RED+"You cannot use this command in this world!");
        
            return true;
        }
        
        int minDistance = 150;
        int maxDistance = 750;
        
        player.teleport(this.getRandomLocation(player, minDistance, maxDistance));
        
        cs.sendMessage(ChatColor.GREEN+"Sending you to a random location in the world!");
        
        return true;
    }
    
    
    public Location getRandomLocation(Player player, int minDistance,int maxDistance) {
        Random random = new Random();
        
        Location lSeed = player.getLocation();
        
        Block bSendPlayerTo = lSeed.add(
            random.nextInt(maxDistance*2-minDistance*2)-minDistance-maxDistance, 
            255-lSeed.getBlockY(), 
            random.nextInt(maxDistance*2-minDistance*2)-minDistance-maxDistance
        ).getBlock();
        
        while(bSendPlayerTo.getType().equals(org.bukkit.Material.AIR) || bSendPlayerTo.getType().equals(org.bukkit.Material.WATER)) {
            if(bSendPlayerTo.getY() == 1 || bSendPlayerTo.getType().equals(org.bukkit.Material.WATER)) {
                // reached the bottom or water, grab a new random location
                bSendPlayerTo = lSeed.add(
                    random.nextInt(maxDistance*2-minDistance*2)-minDistance-maxDistance, 
                    255-lSeed.getBlockY(), 
                    random.nextInt(maxDistance*2-minDistance*2)-minDistance-maxDistance
                ).getBlock();
            }
            else {
                bSendPlayerTo = bSendPlayerTo.getRelative(BlockFace.DOWN);
            }
        }
        
        return bSendPlayerTo.getLocation().add(0,2,0);
    }
}
