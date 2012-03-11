/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors.flagCommandExecutor;
import net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors.pepperoniCommandExecutor;
import net.ellb.plugins.PepperoniProtect.Bukkit.CommandExecutors.protectCommandExecutor;
import net.ellb.plugins.PepperoniProtect.Bukkit.Listeners.PepperoniListener;
import net.ellb.plugins.PepperoniProtect.Helpers.PepperoniGuide;
import net.ellb.plugins.PepperoniProtect.Protection.GlobalAreaManager;
import net.ellb.plugins.PepperoniProtect.Util.FileManager;
import net.ellb.plugins.PepperoniProtect.Util.FlagManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PepperoniProtect extends JavaPlugin {

    static final Logger logger = Logger.getLogger("Minecraft");
    static boolean BETA = true;
    public PepperoniGuide guide;
    public FileManager fileManager;
    public GlobalAreaManager areaManager;
    public PepperoniListener listener;
    public FlagManager flagManager;

    public static boolean isBeta() {
        return BETA;
    }

    public FlagManager getFlagManager() {
        return flagManager;
    }

    public PepperoniListener getListener() {
        return listener;
    }

    public PepperoniGuide getGuide() {
        return guide;
    }

    @Override
    public void onDisable() {
        System.out.println(this + " disabled sir!");
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public GlobalAreaManager getAreaManager() {
        return areaManager;
    }

    public void reloadStuff() {
        fileManager.reloadAreasFile();
        fileManager.reloadConfig();
        flagManager.loadFlags();
    }

    public void registerCommands() {
        flagCommandExecutor flagC = new flagCommandExecutor(this);
        protectCommandExecutor protectC = new protectCommandExecutor(this);
        pepperoniCommandExecutor pepperoniC = new pepperoniCommandExecutor(this);
        this.getCommand("protect").setExecutor(protectC);
        this.getCommand("flag").setExecutor(flagC);
        this.getCommand("pepperoni").setExecutor(pepperoniC);
    }

    @Override
    public void onEnable() {
        fileManager = new FileManager(this);
        guide = new PepperoniGuide(this);
        flagManager = new FlagManager(this);
        areaManager = new GlobalAreaManager(this);
        listener = new PepperoniListener(this);
        if (isBeta() == true) {
            logger.log(Level.INFO, this + " is now enabled. Thanks for testing out!");
        } else {
            logger.log(Level.INFO, "Feel safe, cause the server is protected by ", this);
        }
        this.getServer().getPluginManager().registerEvents(guide, this);
        registerCommands();
        reloadStuff();
        fileManager.saveAreasFile();
        fileManager.saveConfig();
    }
}
