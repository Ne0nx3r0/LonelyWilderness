package com.ne0nx3r0.lonely;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class LonelyWildernessCommandExecutor implements CommandExecutor {
    private final LonelyWilderness plugin;

    public LonelyWildernessCommandExecutor(LonelyWilderness plugin) {
        this.plugin = plugin;
    }
    
    long COOLDOWN = 0;

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        if(!(cs instanceof Player)) {
            cs.sendMessage(ChatColor.RED+"/wild cannot be used from console");
        
            return true;
        }
        
        long now = System.currentTimeMillis();
        
        if(this.COOLDOWN > now) {
            cs.sendMessage(ChatColor.RED+"/wild has a global cooldown for all users");
            
            int seconds = (int) ((this.COOLDOWN - now) / 1000);
            
            cs.sendMessage(ChatColor.RED+"You can try again in "+seconds+" seconds.");
        
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
        
        int maxDistance = 3000;
        
        player.teleport(this.getRandomLocation(player, maxDistance));
        
        cs.sendMessage(ChatColor.GREEN+"Sending you to a random location in the world!");
        
        this.COOLDOWN = System.currentTimeMillis()+10000;
        
        return true;
    }
    
    public Location getRandomLocation(Player player, int maxDistance) {
        Random random = new Random();
        
        Location lSeed = player.getLocation();
        
        int seedX = lSeed.getBlockX();
        int seedZ = lSeed.getBlockZ();
        
        int randomX;
        int randomZ;
        
        Block bSendPlayerTo;
        
        do{
            randomX = seedX + maxDistance - random.nextInt(maxDistance*2);
            randomZ = seedZ + maxDistance - random.nextInt(maxDistance*2);
            
            //preload chunk
            lSeed.getWorld().getChunkAt(randomX/16, randomZ/16).load();
            
            bSendPlayerTo = lSeed.getWorld().getHighestBlockAt(randomX, randomZ);
        }
        while(bSendPlayerTo.getType().equals(Material.WATER) || bSendPlayerTo.getType().equals(Material.AIR));
 
        return bSendPlayerTo.getLocation();
    }
}
