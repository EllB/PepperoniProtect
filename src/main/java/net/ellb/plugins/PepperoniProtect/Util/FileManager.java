/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://www.plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Util;
//The class that will replace PepperoniAreasFile and PepperoniConfiguration - cool, isn't it ;-)

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
    //May move this to the Enums package as well, but who cares...

    public enum PepperoniFile {

        CONFIG, AREAS, BOTH
    };
    public YamlConfiguration Areas;
    public YamlConfiguration Config;
    public File AreasFile = new File("plugins/PepperoniProtect/areas.yml");
    public File ConfigFile = new File("plugins/PepperoniProtect/config.yml");

    public void Load(PepperoniFile pf) {
        if (pf == PepperoniFile.AREAS) {
            Areas = YamlConfiguration.loadConfiguration(AreasFile);
        } else if (pf == PepperoniFile.CONFIG) {
            Config = YamlConfiguration.loadConfiguration(ConfigFile);
        } else {
            Config = YamlConfiguration.loadConfiguration(ConfigFile);
            Areas = YamlConfiguration.loadConfiguration(AreasFile);
        }
    }

    public void Save(PepperoniFile pf) {
        try {
            if (pf == PepperoniFile.AREAS) {
                Areas.save(AreasFile);
            } else if (pf == PepperoniFile.CONFIG) {
                Config.save(ConfigFile);
            } else {
                Config.save(ConfigFile);
                Areas.save(AreasFile);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public YamlConfiguration getConfig(PepperoniFile pf) {
        if (pf == PepperoniFile.AREAS) {
            return Areas;
        } else if (pf == PepperoniFile.CONFIG) {
            return Config;
        } else {
            return null;
        }
    }
}