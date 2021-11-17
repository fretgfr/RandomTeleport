package com.robertdrescher.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TeleportCommand implements CommandExecutor {
    //Boundaries
    private final int MAX_X = 100000;
    private final int MAX_Z = 100000;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("randomteleport")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
                    Location newLocation = overworldWild(player);
                    player.sendMessage("Randomly teleported to " + newLocation.getBlockX() + ", " + newLocation.getBlockY() + ", " + newLocation.getBlockZ());
                } else {
                    player.sendMessage("This command only works in the overworld.");
                }
            }
            return true;
        }
        return false;
    }

    private Location overworldWild(Player player) {
        Random random = new Random();

        double xValue = random.nextInt(MAX_X);
        double zValue = random.nextInt(MAX_Z);
        int yValue = 0;

        //To put them in the middle of the block instad of on the corner
        if (xValue > 0) {
            xValue += .5;
        } else {
            xValue -= .5;
        }

        if (zValue > 0) {
            zValue += .5;
        } else {
            zValue -= .5;
        }

        //Loop through the blocks at the location and see if any of them are safe. Probably if they're Grass
        for (int i = 1; i < 256; i++) {
            Block candidate = player.getWorld().getBlockAt((int) xValue, i, (int) zValue);
            if (candidate.getType().equals(Material.AIR)) {
                if (candidate.getRelative(0, 1, 0).getType().equals(Material.AIR)) {
                    if (candidate.getRelative(0, 2, 0).getType().equals(Material.AIR)) {
                        if (player.getWorld().getBlockAt((int) xValue, i, (int) zValue).getType().equals(Material.GRASS_BLOCK)) {
                            //found a good location. Standing on a grass block with air the two blocks above it.
                            yValue = i;
                            break;
                        }
                    }
                }
            }
        }

        if (yValue == 0) {
            overworldWild(player); // Try a new location
        } else {
            Location wildLocation = new Location(player.getWorld(), xValue, (double) yValue, zValue);
            player.teleport(wildLocation);
            return wildLocation;
        }
        return null;
    }
}
