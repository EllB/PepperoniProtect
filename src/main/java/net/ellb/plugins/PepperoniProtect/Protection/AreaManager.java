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

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import net.ellb.plugins.PepperoniProtect.Util.FileManager.PepperoniFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AreaManager {

    public AreaManager(PepperoniProtect p) {
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
        area.create(L1, L2, owner);
        return area;
    }

    public void SaveAreas() {
        try {
            plugin.getFileManager().getConfig(PepperoniFile.AREAS).save(plugin.getFileManager().AreasFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public void LoadAreas() {
        try {
            plugin.getFileManager().getConfig(PepperoniFile.AREAS).load(plugin.getFileManager().AreasFile);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
        }
    }
}