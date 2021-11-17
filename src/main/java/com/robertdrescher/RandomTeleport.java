package com.robertdrescher;

import com.robertdrescher.commands.TeleportCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTeleport extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("randomteleport").setExecutor(new TeleportCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
