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
import java.util.Set;
import net.ellb.plugins.pepperoniprotect.Util.PepperoniAreasFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AreaManager {

    public PepperoniArea getAreaByUID(String s) {
        PepperoniArea area = new PepperoniArea();
        area.setUID(s);
        area.loadFromFile();
        return area;
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
        PepperoniAreasFile file = new PepperoniAreasFile(new File("plugins/PepperoniProtect/areas.yml"));
        return file.getAreas();
    }

    public PepperoniArea createArea(Location L1, Location L2, Player owner) {
        PepperoniArea area = new PepperoniArea();
        area.init(L1, L2, owner);
        return area;
    }
}