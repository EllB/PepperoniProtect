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
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

    public YamlConfiguration Areas;
    public YamlConfiguration Config;
    public File AreasFile;
    public File ConfigFile;
    public PepperoniProtect plugin;

    public FileManager(PepperoniProtect p) {
        this.plugin = p;

    }

    public YamlConfiguration getAreasConfig() {
        return Areas;
    }

    public YamlConfiguration getConfiguration() {
        return Config;
    }

    public void ReloadConfigFile() {
        if (ConfigFile == null) {
            ConfigFile = new File("plugins/PepperoniProtect/config.yml");
        }
        Config = YamlConfiguration.loadConfiguration(ConfigFile);
        InputStream defConfigStream = plugin.getResource("config.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            Config.setDefaults(defConfig);
        }
        try {
            Config.save(ConfigFile);
        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void RealodAreasFile() {
        if (AreasFile == null) {
            AreasFile = new File("plugins/PepperoniProtect/areas.yml");
        }
        Areas = YamlConfiguration.loadConfiguration(AreasFile);
        InputStream defConfigStream = plugin.getResource("areas.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            Areas.setDefaults(defConfig);
        }
        try {
            Areas.save(AreasFile);
        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}