/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://www.plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors;

import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Helpers.PepperoniGuide;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class protectCommandExecutor implements CommandExecutor {

    public PepperoniProtect plugin;
    private PepperoniGuide guide;

    public protectCommandExecutor(PepperoniProtect p) {
        this.guide = p.getGuide();
    }

    public boolean onCommand(CommandSender sender, Command cmnd, String string, String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("pepperoni.protect")) {
                if (guide.getProtectionStage(p) == 0) {
                    guide.startProtection(p);
                } else if (guide.getProtectionStage(p) == 1) {
                    guide.confirmProtection(p);
                } else if (guide.getProtectionStage(p) == 2) {
                    guide.finishProtection(p);
                } else {
                    throw new IndexOutOfBoundsException("Programming error, bug EllB about it. ");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You are not allowed to protect your own areas!");
                p.sendMessage("(Sorry for that D:)");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You are not human-ish enough to use that command (go ingame).");
        }
        return true;
    }
}