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
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flagCommandExecutor implements CommandExecutor {

    PepperoniProtect plugin;
    public GlobalAreaManager areaManager;

    public flagCommandExecutor(PepperoniProtect p) {
        this.plugin = p;
        this.areaManager = p.getAreaManager();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (args.length < 1) {
                sendFlagInfo(player);
            }
            if (args.length > 2) {
                player.sendMessage(ChatColor.RED + "Too many arguments! Correct usage: /" + label + " [flag] [value]");
            }
            if (args.length == 1) {
                if (areaManager.getArea(player.getLocation()) != null) {
                    areaManager.getArea(player.getLocation()).setFlag(args[0], args[1]);
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "I'm not gonna let you use that command, cause you're outside an area. Additionally you're still not human-ish enough.");
        }
        return true;
    }

    public void sendFlagInfo(Player p) {
        p.sendMessage("To set a flag, type " + ChatColor.DARK_GREEN + "/flag [FLAGNAME] [VALUE]" + ChatColor.WHITE + ". Where " + ChatColor.DARK_GREEN + "[FLAGNAME]" + ChatColor.WHITE + " is the name of the flag you want to set, and " + ChatColor.DARK_GREEN + "[VALUE]" + ChatColor.WHITE + "is the value you want to set it to. ");
        p.sendMessage("-- Aviable Flags --");
        for (PepperoniAreaFlagInfo flag : plugin.getFileManager().getConfiguration().getFlagInfos()) {
            p.sendMessage(ChatColor.DARK_GREEN + flag.getDisplayName() + ChatColor.WHITE + " " + flag.getDescription());
        }
    }
}