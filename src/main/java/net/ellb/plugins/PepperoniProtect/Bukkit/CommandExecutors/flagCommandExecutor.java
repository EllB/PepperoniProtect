/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors;

import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Util.PepperoniAreaFlagInfo;
import net.ellb.plugins.PepperoniProtect.Protection.GlobalAreaManager;
import net.ellb.plugins.PepperoniProtect.Util.FlagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flagCommandExecutor implements CommandExecutor {

    PepperoniProtect plugin;
    private GlobalAreaManager areaManager;
    private FlagManager flagManager;
    private static String instructions = "To set a flag, type " + ChatColor.DARK_GREEN + "/flag [FLAGNAME] [VALUE]" + ChatColor.WHITE + ". Where " + ChatColor.DARK_GREEN + "[FLAGNAME]" + ChatColor.WHITE + " is the name of the flag you want to set, and " + ChatColor.DARK_GREEN + "[VALUE]" + ChatColor.WHITE + "is the value you want to set it to.";

    public flagCommandExecutor(PepperoniProtect p) {
        this.plugin = p;
        this.areaManager = p.getAreaManager();
        this.flagManager = p.getFlagManager();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            PepperoniAreaFlagInfo flag = flagManager.getFlagByDisplayName(args[0]);
            if (flag == null) {
                player.sendMessage(ChatColor.RED + "That flag does not exist, please check your spelling.");
            } else {
                if (player.hasPermission("pepperoni.flag.set." + flag.getPath())) {
                    if (areaManager.getArea(player.getLocation()) == null) {
                        player.sendMessage(ChatColor.RED + "You need to be inside an area to be able to change that flag.");
                    } else {
                        if (!canChange(player)) {
                            player.sendMessage(ChatColor.RED + "This isn't your protection, so why would you be able to modify it? (You are not allowed)");
                        } else {
                            switch (args.length) {
                                case 0:
                                    sendFlagInfo(player);
                                case 1:
                                    player.sendMessage(instructions);
                                case 2:
                                    areaManager.getArea(player.getLocation()).setFlag(flag.getPath(), args);
                                    player.sendMessage("Succesfully changed area flag " + flag.getDisplayName() + " to " + args[1]);
                                default:
                                    player.sendMessage(ChatColor.RED + "Too many arguments:");
                                    player.sendMessage(instructions);
                            }
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Sorry, but you aren't allowed to set that flag.");
                }
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Sorry, you are not a real player, just a console. Please go in-game.");
        }
        return true;
    }

    //Some mini-helpers
    private boolean canChange(Player p) {
        if (!p.hasPermission("pepperoni.admin")) {
            if (!areaManager.getArea(p.getLocation()).getStringFlag("owner").equalsIgnoreCase(p.getName())) {
                return false;
            }
        }
        return true;
    }

    private void sendFlagInfo(Player p) {
        p.sendMessage("-- Aviable Flags --");
        for (PepperoniAreaFlagInfo flag : plugin.getFlagManager().getFlags()) {
            p.sendMessage(ChatColor.DARK_GREEN + flag.getDisplayName() + ChatColor.WHITE + " -- " + flag.getDescription());
        }
    }
}
