/*
 * PepperoniProtect
 * Copyright (C) 2012 EllB <http://www.ellb.net/>
 *
 * This program is a part of The SpicyPack and is
 * therefore licensed under the SpicyCode custom
 * license <http://plugins.ellb.net/license/>.
 *
 */
package net.ellb.plugins.PepperoniProtect.Protection;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Util.FileManager;
import net.ellb.plugins.PepperoniProtect.Util.PepperoniAreaFlagInfo;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PepperoniArea {

    public Location L1;
    public Location L2;
    public String UID;
    static String filePath = "plugins/PepperoniProtect/areas.yml";
    public FileManager fileManager;
    public Player owner;
    public PepperoniProtect plugin;
    public FileConfiguration config;

    public PepperoniArea(PepperoniProtect p, Location p1, Location p2, Player owner, String uid) {
        this.plugin = p;
        this.config = plugin.getFileManager().getAreasFile();
        L1 = p1;
        L2 = p2;
        this.UID = uid;
        for (PepperoniAreaFlagInfo inf : plugin.getFlagManager().getFlags()) {
            defaultFlag(inf, p1, p2, owner);
        }
    }

    public String getUID() {
        return UID;
    }

    private void defaultFlag(PepperoniAreaFlagInfo pafi, Location p1, Location p2, Player owner) {
        Object value = null;
        if (pafi.getDefault().toString().equalsIgnoreCase("[DEFAULT]")) {
            if (pafi.getPath().equalsIgnoreCase("position1/y")) {
                value = p1.getBlockY();
            } else if (pafi.getPath().equalsIgnoreCase("position1/x")) {
                value = p1.getBlockX();
            } else if (pafi.getPath().equalsIgnoreCase("position2/y")) {
                value = p2.getBlockY();
            } else if (pafi.getPath().equalsIgnoreCase("position2/x")) {
                value = p2.getBlockX();
            } else if (pafi.getPath().equalsIgnoreCase("owner")) {
                value = owner.getName();
            } else if (pafi.getPath().equalsIgnoreCase("world")) {
                value = p1.getWorld().getName();
            }
        } else {
            value = pafi.getDefault();
        }
        this.setFlag(pafi.getPath(), value);
    }

    public int getSize() {
        return getYBlocks() * getXBlocks();
    }

    public int getXBlocks() {
        if (L1.getBlockX() > L2.getBlockX()) {
            return L1.getBlockX() - L2.getBlockX();
        }
        if (L2.getBlockX() > L1.getBlockX()) {
            return L2.getBlockX() - L1.getBlockX();
        }
        return 1;
    }

    public int getYBlocks() {
        if (L1.getBlockY() > L2.getBlockY()) {
            return L1.getBlockY() - L2.getBlockY();
        }
        if (L2.getBlockY() > L1.getBlockY()) {
            return L2.getBlockY() - L1.getBlockY();
        }
        return 1;
    }

    public String getStringFlag(String flag) {
        if (flag == null) {
            return null;
        }
        if (config.getString(this.getUID() + "." + flag) == null) {
            return null;
        }
        return config.getString(this.getUID() + "." + flag);
    }

    public List<String> getStringListFlag(String flag) {
        if (flag == null) {
            return null;
        }
        if (config.getStringList(this.getUID() + "." + flag) == null) {
            return null;
        }
        return config.getStringList(this.getUID() + "." + flag);
    }

    public Boolean getBooleanFlag(String flag) {
        if (flag == null) {
            return null;
        }
        if (config.getString(this.getUID() + "." + flag) == null) {
            return null;
        }
        return config.getBoolean(this.getUID() + "." + flag);
    }

    public Integer getIntegerFlag(String flag) {
        if (flag == null) {
            return null;
        }
        if (config.getInt(this.getUID() + "." + flag, 0) == 0) {
            return null;
        }
        return config.getInt(this.getUID() + "." + flag);
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
        return result;
    }

    public boolean contains(Location loc) {
        if (loc.getWorld() == this.L1.getWorld()) {
            //Super-duper not fancy algorithm to calculate if the area contains the location, I don't even know if it works...
            if (loc.getY() <= L1.getY() && loc.getY() >= L1.getX() && loc.getX() <= L2.getY() && loc.getY() == L2.getX()) {
                return true;
            }
        }
        return false;
    }
}