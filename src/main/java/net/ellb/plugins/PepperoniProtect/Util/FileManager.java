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
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

    public PepperoniAreasFile Areas;
    public PepperoniConfigurationFile Config;
    public File AreasFile;
    public File ConfigFile = new File("");
    public PepperoniProtect plugin;
    static final Logger logger = Logger.getLogger("Minecraft");

    public FileManager(PepperoniProtect p) {
        this.plugin = p;
        this.ReloadConfigFile();
        Areas = new PepperoniAreasFile();
        Config = new PepperoniConfigurationFile(plugin);
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
        if (!ConfigFile.exists()) {
            ConfigFile = new File("plugins/PepperoniProtect/config.yml");
            try {
                try {
                    InputStream defConfigStream = plugin.getResource("customConfig.yml");
                    if (defConfigStream != null) {
                        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                        Config.setDefaults(defConfig);
                    }
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Error when loading default configuration. ", ex);
                }
                Config.save(ConfigFile);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error when creting default configuration file. ", ex);
            }
        } else {
            try {
                Config.load(ConfigFile);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error when loading configuration file. ", ex);
            }
        }
    }

    public void RealodAreasFile() {
        if (AreasFile == null) {
            AreasFile = new File("plugins/PepperoniProtect/areas.yml");
            Areas.save();
        }
        Areas.load();
    }
}