/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://www.plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Bukkit.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EndermanPickupEvent;
import org.bukkit.event.entity.EndermanPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
/*
 * Massive class listening for griefing, smart? ;-)
 */
public class GriefListener implements Listener {

    @EventHandler
    public void BLOCK_BREAK(BlockBreakEvent e) {
    }

    @EventHandler
    public void BLOCK_PLACE(BlockPlaceEvent e) {
    }

    @EventHandler
    public void BUCKET_EMPTY(PlayerBucketEmptyEvent e) {
    }

    @EventHandler
    public void BUCKET_FILL(PlayerBucketFillEvent e) {
    }

    @EventHandler
    public void PISTON_RETRACT(BlockPistonRetractEvent e) {
    }

    @EventHandler
    public void PISTON_RETRACT(BlockPistonExtendEvent e) {
    }

    @EventHandler
    public void PAINTING_BREAK(PaintingBreakEvent e) {
    }

    @EventHandler
    public void PVP(EntityDamageEvent e) {
    }

    @EventHandler
    public void MOB_SPAWN(CreatureSpawnEvent e) {
    }

    @EventHandler
    public void ENDERMAN_PICK(EndermanPickupEvent e) {
        
    }
    @EventHandler
    public void ENDERMAN_PLACE(EndermanPlaceEvent e){
        
    }
    @EventHandler
    public void BLOCK_IGNITE(BlockIgniteEvent e){
        
    }
    @EventHandler
    public void BLOCK_BURN(BlockBurnEvent e){
        
    }
}