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

import java.util.List;
import java.util.Random;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Util.PepperoniAreasFile;
import net.ellb.plugins.PepperoniProtect.Util.FileManager;
import net.ellb.plugins.PepperoniProtect.Util.PepperoniAreaFlagInfo;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PepperoniArea {

    public Location L1;
    public Location L2;
    public String UID;
    static String filePath = "plugins/PepperoniProtect/areas.yml";
    public FileManager fileManager;
    public Player owner;
    public PepperoniProtect plugin;
    public PepperoniAreasFile config;

    public PepperoniArea(PepperoniProtect p, Location p1, Location p2, Player owner, String uid) {
        this.plugin = p;
        this.config = plugin.getFileManager().getAreasFile();
        L1 = p1;
        L2 = p2;
        this.UID = uid;
        for (PepperoniAreaFlagInfo inf : plugin.getFileManager().getConfiguration().getFlagInfos()) {
            if (inf.getDefault() instanceof String) {
                String def = inf.getDefault().toString();
                def.replace("loc1Y", Double.toString(p1.getY()));
                def.replace("loc1X", Double.toString(p1.getX()));
                def.replace("loc2Y", Double.toString(p2.getY()));
                def.replace("loc2X", Double.toString(p2.getX()));
                def.replace("owner", owner.getName());
            }
            this.setFlag(inf.getPath(), inf.getDefault());
        }
    }

    public String getUID() {
        return UID;
    }

    public String getStringFlag(String flag) {
        return config.getStringFlag(this, flag);
    }

    public List<String> getStringListFlag(String flag) {
        return config.getStringListFlag(this, flag);
    }

    public boolean getBooleanFlag(String flag) {
        return config.getBooleanFlag(this, flag);
    }

    public int getIntegerFlag(String flag) {
        return config.getIntFlag(this, flag);
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