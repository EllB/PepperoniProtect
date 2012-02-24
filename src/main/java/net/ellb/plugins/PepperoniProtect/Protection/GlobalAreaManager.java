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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GlobalAreaManager {

    public GlobalAreaManager(PepperoniProtect p) {
        this.plugin = p;
    }
    public PepperoniProtect plugin;
    public Set<PepperoniArea> areas = new HashSet<PepperoniArea>();

    public PepperoniArea getAreaByUID(String s) {
        for (PepperoniArea a : this.getAreas()) {
            if (s.equals(a.getUID())) {
                return a;
            }
        }
        return null;
    }

    public boolean can(Player p, Location loc, String action) {
        PepperoniArea area = this.getArea(loc);
        if (!area.getStringFlag("owner").equals(p.getName())) {
            if (!area.getStringListFlag("members").contains(p.getName()) && !area.getBooleanFlag(action + ".members")) {
                if (!area.getBooleanFlag(action + ".outsider")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean should(Location loc, String action) {
        return getArea(loc).getBooleanFlag(action);
    }

    public PepperoniArea getArea(Location loc) {
        //TODO: Make this more fancy with parent-and-child areas:
        for (PepperoniArea p : this.getAreas()) {
            if (p.contains(loc)) {
                return p;
            }
        }
        return null;
    }

    public Set<PepperoniArea> getAreas() {
        return areas;
    }

    public PepperoniArea createArea(Location L1, Location L2, Player owner) {
        PepperoniArea area = new PepperoniArea(plugin);
        String uid = generateUID();
        area.create(L1, L2, owner, uid);
        return area;
    }

    public String generateUID() {
        Random rand = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWabcdefghijklmnopqrstuvwxyz1234567890";
        String result;
        int length = 4;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars.charAt(rand.nextInt(chars.length()));
        }
        result = new String(text);
        if (plugin.getFileManager().getAreasFile().getKeys(true) != null) {
            if (plugin.getFileManager().getAreasFile().getKeys(true).contains(result)) {
                return generateUID();
            }
        }
        return result;
    }

    public void SaveAreas() {
        try {
            plugin.getFileManager().getAreasFile().save();
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public void LoadAreas() {
        try {
            plugin.getFileManager().getAreasFile().load();
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
        }
    }
}