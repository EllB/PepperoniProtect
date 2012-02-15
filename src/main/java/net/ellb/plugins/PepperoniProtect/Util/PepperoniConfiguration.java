package net.ellb.plugins.pepperoniprotect.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

public class PepperoniConfiguration {

    public PepperoniConfiguration(File sFile) {
        this.file = sFile;
    }
    public Map<String, Object> values = new HashMap<String, Object>();
    public File file;
    public YamlConfiguration config = new YamlConfiguration();
    static final Logger logger = Logger.getLogger("minecraft");

    public void save() {
        try {
            config.save(file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error when saving the configuration file. ", ex);
        }
    }
}