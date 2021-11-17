package com.robertdrescher;

import com.robertdrescher.commands.TeleportCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Registering randomteleport command.");
        this.getCommand("randomteleport").setExecutor(new TeleportCommand());
        System.out.println("Done.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
