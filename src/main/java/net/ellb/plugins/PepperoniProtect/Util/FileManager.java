/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Util;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;

public class FileManager {

    public PepperoniAreasFile Areas;
    public PepperoniConfigurationFile Config;
    public File AreasFile;
    public File ConfigFile = new File("plugins/PepperoniProtect/config.yml");
    public PepperoniProtect plugin;
    static final Logger logger = Logger.getLogger("Minecraft");

    public FileManager(PepperoniProtect p) {
        this.plugin = p;
        Areas = new PepperoniAreasFile();
        Config = new PepperoniConfigurationFile(plugin);
        this.ReloadConfigFile();
    }

    public PepperoniAreasFile getAreasFile() {
        return Areas;
    }

    public PepperoniConfigurationFile getConfiguration() {
        if (!ConfigFile.exists()) {
            return Config;
        } else {
            Config = Config.getDefault();
            return Config;
        }
    }

    public void ReloadConfigFile() {
        logger.log(Level.INFO, "ReloadConfigFile method started...");
        if (!ConfigFile.exists()) {
            //TEMP:
            logger.log(Level.INFO, "Configuration not found. ");
            plugin.saveDefaultConfig();
            try {
                Config.load(ConfigFile);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Config.load(ConfigFile);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error when loading configuration file. ", ex);
            }
        }
        Config.loadFlagInfos();
    }

    public void RealodAreasFile() {
        if (!ConfigFile.exists()) {
            Areas = new PepperoniAreasFile();
            try {
                Areas.save(ConfigFile);
                Areas.load(ConfigFile);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Areas.load(ConfigFile);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error when loading areas file. ", ex);
            }
        }
        
    }
}