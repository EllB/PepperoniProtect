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

import java.io.File;
import java.util.Random;
import net.ellb.plugins.pepperoniprotect.Util.PepperoniAreasFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PepperoniArea {

    public Location L1;
    public Location L2;
    static String filePath = "plugins/PepperoniProtect/areas.yml";
    public PepperoniAreasFile aFile = new PepperoniAreasFile(new File(filePath));
    public Player owner;

    public void init(Location p1, Location p2, Player p) {
        L1 = p1;
        L2 = p2;
        this.owner = p;
        Random rand = new Random();
        aFile.set(this, "uid", generateUID(rand));
    }

    public String getUID() {
        return aFile.get("uid").toString();
    }

    public String getFlag(String flag) {
        return aFile.get(flag).toString();
    }

    public void setUID(String uid) {
        aFile.set(this, "uid", uid);
    }

    public void setFlag(String flag, Object value) {
        aFile.set(this, flag, value);
    }

    public void saveToFile() {
        aFile.Save();
    }

    public void loadFromFile() {
        aFile.Load();
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
        if (aFile.keyExists(new String(text))) {
            return generateUID(new Random());
        } else {
            return new String(text);
        }
    }

    public void delete() {
        aFile.getConfig().set(this.getUID(), "");
    }

    public boolean contains(Location loc) {
        if (loc.getY() <= L1.getY() && loc.getY() >= L1.getX() && loc.getX() <= L2.getY() && loc.getY() == L2.getX()) {
            return true;
        } else {
            return false;
        }
    }
}