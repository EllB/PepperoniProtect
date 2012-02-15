/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://www.plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.pepperoniprotect.Util;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Protection.PepperoniArea;
import org.bukkit.configuration.file.YamlConfiguration;

public class PepperoniAreasFile {

    public PepperoniAreasFile(File sFile) {
        this.file = sFile;
        config = YamlConfiguration.loadConfiguration(file);
    }
    public YamlConfiguration config;
    public File file;
    static final Logger logger = Logger.getLogger("Minecraft");

    public YamlConfiguration getConfig() {
        return config;
    }

    public void Save() {
        try {
            config.save(file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public Set<PepperoniArea> getAreas() {
        Set<String> uids = config.getKeys(true);
        Set<PepperoniArea> areas = null;
        for (String s : config.getKeys(true)) {
            //TODO
        }
        return areas;
    }

    public boolean keyExists(String s) {
        if (config.getKeys(true).contains(s)) {
            return true;
        } else {
            return false;
        }
    }

    public void Load() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void set(PepperoniArea area, String prop, Object val) {
        config.set(area.getUID() + "." + prop, val);
    }

    public Object get(String path) {
        return config.get(path);
    }
}