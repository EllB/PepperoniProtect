/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Bukkit.Listeners;

import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Protection.GlobalAreaManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EndermanPickupEvent;
import org.bukkit.event.entity.EndermanPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
/*
 * Massive class listening for griefing, smart? ;-)
 */

public class GriefListener implements Listener {

    public PepperoniProtect plugin;
    public GlobalAreaManager areaManager;

    public GriefListener(PepperoniProtect p) {
        this.plugin = p;
        this.areaManager = p.getAreaManager();
    }

    @EventHandler
    public void BLOCK_BREAK(BlockBreakEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.can(e.getPlayer(), e.getBlock().getLocation(), "build")) {
            e.getPlayer().sendMessage(ChatColor.RED + "Sorry, you are not allowed to break blocks here.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BLOCK_PLACE(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.can(e.getPlayer(), e.getBlock().getLocation(), "build")) {
            e.getPlayer().sendMessage(ChatColor.RED + "Sorry, you are not allowed to place blocks here.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BUCKET_EMPTY(PlayerBucketEmptyEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getBucket() == Material.LAVA_BUCKET) {
            if (!areaManager.can(e.getPlayer(), e.getBlockClicked().getRelative(e.getBlockFace()).getLocation(), "liquids.lava.place")) {
                e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to place lava here. ");
                e.setCancelled(true);
                return;
            }
        } else if (e.getBucket() == Material.WATER_BUCKET) {
            if (!areaManager.can(e.getPlayer(), e.getBlockClicked().getRelative(e.getBlockFace()).getLocation(), "liquids.water.place")) {
                e.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to place water here. ");
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void BUCKET_FILL(PlayerBucketFillEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getBucket() == Material.LAVA_BUCKET) {
            if (!areaManager.can(e.getPlayer(), e.getBlockClicked().getRelative(e.getBlockFace()).getLocation(), "liquids.lava.take")) {
                e.getPlayer().sendMessage(ChatColor.RED + "That lava should be as it is (you are not allowed to take it).");
                e.setCancelled(true);
                return;
            }
        } else if (e.getBucket() == Material.WATER_BUCKET) {
            if (!areaManager.can(e.getPlayer(), e.getBlockClicked().getRelative(e.getBlockFace()).getLocation(), "liquids.water.take")) {
                e.getPlayer().sendMessage(ChatColor.RED + "Isn't the water  beatiful as it is? (You are not allowed to take it.)");
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void PISTON_MOVE(BlockPistonEvent e) {
        if (e.isCancelled()) {
            return;
        }

        //TODO: Maybe check for affected blocks and then block outside-from-area-piston-griefing.
        if (!areaManager.should(e.getBlock().getLocation(), "piston_move")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PVP(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        //Don't really know if this works, but I will go for it for now. 
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damager = (Player) e.getDamager();
            if (!areaManager.should(e.getEntity().getLocation(), "pvp")) {
                damager.sendMessage(ChatColor.RED + "You are not allowed to hurt this person. ");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void MOB_SPAWN(CreatureSpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.should(e.getLocation(), "mobs.spawn")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ENDERMAN_PICK(EndermanPickupEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.should(e.getBlock().getLocation(), "enderman_grief")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void ENDERMAN_PLACE(EndermanPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.should(e.getLocation(), "enderman_grief")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BLOCK_IGNITE(BlockIgniteEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.can(e.getPlayer(), e.getBlock().getLocation(), "fire.ignite")) {
            e.getPlayer().sendMessage(ChatColor.RED + "No, no, don't burn things. Bad " + e.getPlayer().getDisplayName() + ".");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BLOCK_BURN(BlockBurnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.should(e.getBlock().getLocation(), "fire.burnblock")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void BOOM(EntityExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!areaManager.should(e.getLocation(), "boom")) {
            e.setCancelled(true);
        }
    }
}