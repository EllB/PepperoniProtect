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
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

    public enum PepperoniFile {
        CONFIG, AREAS, BOTH
    };
    public YamlConfiguration Areas;
    public YamlConfiguration Config;
    public File AreasFile = new File("plugins/");
    public File ConfigFile = new File("");

    public void Load(PepperoniFile pf) {
        if(pf == PepperoniFile.AREAS){
            Areas = YamlConfiguration.loadConfiguration(AreasFile);
        }else if (pf == PepperoniFile.CONFIG){
            Config = YamlConfiguration.loadConfiguration(ConfigFile);
        }else{
            Config = YamlConfiguration.loadConfiguration(ConfigFile);
            Areas = YamlConfiguration.loadConfiguration(AreasFile);
        }
    }
    
}