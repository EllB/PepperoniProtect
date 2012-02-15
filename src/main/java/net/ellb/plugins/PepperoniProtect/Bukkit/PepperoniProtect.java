/*
* PepperoniProtect
* Copyright (C) 2012 EllB <http://www.ellb.net/>
* 
* This program is a part of The SpicyPack and is
* therefore licensed under the SpicyCode custom
* license <http://www.plugins.ellb.net/license/>.
*
*/

package net.ellb.plugins.PepperoniProtect.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors.protectCommandExecutor;
import net.ellb.plugins.PepperoniProtect.Helpers.PepperoniGuide;
import org.bukkit.plugin.java.JavaPlugin;

public class PepperoniProtect extends JavaPlugin {
    static final Logger logger = Logger.getLogger("Minecraft");
    public PepperoniGuide guide = new PepperoniGuide();
    static boolean BETA = true;

    @Override
    public void onDisable() {
        System.out.println("[PepperoniProtect] " + this + " disabled sir!");
    }

    public void registerCommands() {
        protectCommandExecutor protectC = new protectCommandExecutor(guide);
        this.getCommand("protect").setExecutor(protectC);
    }

    @Override
    public void onEnable() {
        if (BETA == false) {
            logger.log(Level.INFO, "Feel safe, cause the server is protected by ", this);
        } else {
            logger.log(Level.INFO, "Thanks for testing out ", this + ". Please report any bugs or requests to EllB. ");
        }
        this.getServer().getPluginManager().registerEvents(guide, this);
        registerCommands();
    }
}