/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://www.plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Helpers;

import java.util.HashMap;
import java.util.Map;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Enums.MsgType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PepperoniGuide implements Listener {

    public PepperoniProtect plugin;
    public Map<Player, Integer> protectionStage = new HashMap<Player, Integer>();
    public Map<Player, Integer> torch = new HashMap<Player, Integer>();
    public Map<Player, Location> torch1 = new HashMap<Player, Location>();
    public Map<Player, Location> torch2 = new HashMap<Player, Location>();
    static boolean giveTorches = true;
    static Material protectionBlock = Material.REDSTONE_TORCH_ON;
    static boolean economy = false;

    public PepperoniGuide(PepperoniProtect p) {
        this.plugin = p;
    }

    public void startProtection(Player p) {
        if (giveTorches == true) {
            p.getInventory().addItem(new ItemStack(protectionBlock, 2));
        }
        setProtectionStage(p, 1);
        sendMessage(MsgType.ASSISTANT, "Protection of area started. Place two redstone torches, then type" + ChatColor.DARK_GREEN + "/protect" + ChatColor.DARK_GREEN + " again to protect the area. ", p);
        torch.put(p, 1);
    }

    public void cancelProtection(Player p) {
        sendMessage(MsgType.WARNING, "Protection of area canceled.", p);
        takeTorches(p);
        setProtectionStage(p, 0);
    }

    public void setProtectionStage(Player p, Integer i) {
        protectionStage.put(p, i);
    }

    public int getProtectionStage(Player p) {
        return protectionStage.get(p);
    }

    public void confirmProtection(Player p) {
        setProtectionStage(p, 2);
        sendMessage(MsgType.ASSISTANT, "Area selected, are you sure you want to protect the area? Type " + ChatColor.DARK_GREEN + "/protect" + ChatColor.WHITE + " to protect the area, or break any block to cancel the protection. ", p);
    }

    public void finishProtection(Player p) {
        saveArea(p);
        sendMessage(MsgType.ASSISTANT, "You have now successfully protected an area! For more info on what to do with your area, type " + ChatColor.DARK_GREEN + "/protection" + ChatColor.WHITE + ".", p);
        setProtectionStage(p, 0);
        takeTorches(p);
    }

    public void saveArea(Player p) {
        plugin.getAreaManager().createArea(torch1.get(p), torch2.get(p), p);
        plugin.getAreaManager().SaveAreas();
    }

    public void takeTorches(Player p) {
        if (torch1.get(p) != null) {
            p.getWorld().getBlockAt(torch1.get(p)).setType(Material.AIR);
        }
        if (torch2.get(p) != null) {
            p.getWorld().getBlockAt(torch2.get(p)).setType(Material.AIR);
        }
    }

    public void flipTorch(Player p) {
        if (torch.get(p) == 1) {
            torch.put(p, 2);
        } else {
            torch.put(p, 1);
        }
    }

    public void sendMessage(MsgType mt, String msg, Player pl) {
        if (mt == MsgType.ASSISTANT) {
            pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.DARK_PURPLE + "Protecting Assistant" + ChatColor.WHITE + "] " + msg);
        }
        if (mt == MsgType.INFO) {
            pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.BLUE + "Protection Info" + ChatColor.WHITE + "] " + msg);
        }
        if (mt == MsgType.WARNING) {
            pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "Protection Warning" + ChatColor.WHITE + "] " + msg);
        }

    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == protectionBlock) {
            if (protectionStage.get(e.getPlayer()) == 1) {
                if (torch.get(e.getPlayer()) == 1) {
                    torch1.put(e.getPlayer(), e.getBlock().getLocation());
                } else if (torch.get(e.getPlayer()) == 2) {
                    torch2.put(e.getPlayer(), e.getBlock().getLocation());
                    confirmProtection(e.getPlayer());
                }
                flipTorch(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        setProtectionStage(e.getPlayer(), 0);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != protectionBlock) {
            cancelProtection(e.getPlayer());
        }
    }
}