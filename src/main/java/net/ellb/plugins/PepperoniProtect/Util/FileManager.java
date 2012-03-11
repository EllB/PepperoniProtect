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
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

    public FileConfiguration Areas;
    public FileConfiguration Config;
    public File AreasFile = null;
    public File ConfigFile = null;
    public PepperoniProtect plugin;
    static final Logger logger = Logger.getLogger("Minecraft");

    public FileManager(PepperoniProtect p) {
        this.plugin = p;
    }

    public FileConfiguration getAreasFile() {
        if (Areas == null) {
            reloadAreasFile();
        }
        return Areas;
    }

    public FileConfiguration getConfiguration() {
        if (Config == null) {
            reloadConfig();
        }
        return Config;
    }

    public void reloadConfig() {
        if (ConfigFile == null) {
            ConfigFile = new File(plugin.getDataFolder(), "config.yml");
        }
        Config = YamlConfiguration.loadConfiguration(ConfigFile);
        Areas.options().copyDefaults(true);
        InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            Config.setDefaults(defConfig);
        }
    }

    public void saveConfig() {
        if (Config == null || ConfigFile == null) {
            return;
        }
        Config.options().copyDefaults(true);
        try {
            Config.save(ConfigFile);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error when saving config.yml", ex);
        }
    }

    public void saveAreasFile() {
        if (Areas == null || AreasFile == null) {
            return;
        }
        try {
            Areas.save(AreasFile);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error when saving areas.yml", ex);
        }
    }

    public void reloadAreasFile() {
        if (AreasFile == null) {
            AreasFile = new File(plugin.getDataFolder(), "areas.yml");
        }
        Areas = YamlConfiguration.loadConfiguration(AreasFile);
        InputStream defConfigStream = plugin.getResource("areas.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            Areas.setDefaults(defConfig);
        }
    }
}
