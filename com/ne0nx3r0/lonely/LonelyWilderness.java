package com.ne0nx3r0.lonely;

import org.bukkit.plugin.java.JavaPlugin;

public class LonelyWilderness extends JavaPlugin {
    
    @Override
    public void onEnable() {
        this.getCommand("wild").setExecutor(new LonelyWildernessCommandExecutor(this));
    }
}
