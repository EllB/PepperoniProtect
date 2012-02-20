/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 * 
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://www.plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Protection;

import java.util.List;
import java.util.Random;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Util.FileManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class PepperoniArea {

    public Location L1;
    public Location L2;
    public String UID;
    static String filePath = "plugins/PepperoniProtect/areas.yml";
    public FileManager fileManager;
    public Player owner;
    public PepperoniProtect plugin;
    public YamlConfiguration config;

    public PepperoniArea(PepperoniProtect p) {
        this.plugin = p;
        config = plugin.getFileManager().getAreasConfig();
    }

    public void create(Location p1, Location p2, Player p) {
        L1 = p1;
        L2 = p2;
        this.owner = p;
        Random rand = new Random();
        this.UID = this.generateUID(rand);
    }

    public String getUID() {
        return UID;
    }

    public String getStringFlag(String flag) {
        return config.getString(flag);
    }

    public List<String> getStringListFlag(String flag) {
        return config.getStringList(flag);
    }

    public boolean getBooleanFlag(String flag) {
        return config.getBoolean(flag);
    }

    public int getIntegerFlag(String flag) {
        return config.getInt(flag);
    }

    public void setUID(String uid) {
        this.UID = uid;
    }

    public void setFlag(String flag, Object value) {
        config.set(this.getUID() + "." + flag, value);
    }

    public String generateUID(Random rand) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWabcdefghijklmnopqrstuvwxyz1234567890";
        String result;
        int length = 4;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars.charAt(rand.nextInt(chars.length()));
        }
        result = new String(text);
        return generateUID(new Random());
    }

    public boolean contains(Location loc) {
        if (loc.getY() <= L1.getY() && loc.getY() >= L1.getX() && loc.getX() <= L2.getY() && loc.getY() == L2.getX()) {
            return true;
        } else {
            return false;
        }
    }
}