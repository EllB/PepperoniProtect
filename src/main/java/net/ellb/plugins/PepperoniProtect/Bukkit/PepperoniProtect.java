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
import net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors.flagCommandExecutor;
import net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors.protectCommandExecutor;
import net.ellb.plugins.PepperoniProtect.Bukkit.Listeners.GriefListener;
import net.ellb.plugins.PepperoniProtect.Helpers.PepperoniGuide;
import net.ellb.plugins.PepperoniProtect.Protection.AreaManager;
import net.ellb.plugins.PepperoniProtect.Util.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PepperoniProtect extends JavaPlugin {

    static final Logger logger = Logger.getLogger("Minecraft");
    static boolean BETA = true;
    public PepperoniGuide pepperoniGuide;
    public FileManager fileManager;
    public AreaManager areaManager;
    public GriefListener gl;

    @Override
    public void onDisable() {
        System.out.println("[PepperoniProtect] " + this + " disabled sir!");
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public PepperoniGuide getGuide() {
        return pepperoniGuide;
    }

    public void registerCommands() {
        flagCommandExecutor flagC = new flagCommandExecutor();
        protectCommandExecutor protectC = new protectCommandExecutor(this);
        this.getCommand("protect").setExecutor(protectC);
        this.getCommand("flag").setExecutor(flagC);
    }

    @Override
    public void onEnable() {
        fileManager = new FileManager(this);
        pepperoniGuide = new PepperoniGuide(this);
        areaManager = new AreaManager(this);
        gl = new GriefListener();
        if (BETA == false) {
            logger.log(Level.INFO, "Feel safe, cause the server is protected by ", this);
        } else {
            logger.log(Level.INFO, "Thanks for testing out ", this + ". Please report any bugs or requests to EllB. ");
        }
        this.getServer().getPluginManager().registerEvents(pepperoniGuide, this);
        this.getServer().getPluginManager().registerEvents(gl, this);
        registerCommands();
        fileManager.RealodAreasFile();
        fileManager.ReloadConfigFile();
    }
}