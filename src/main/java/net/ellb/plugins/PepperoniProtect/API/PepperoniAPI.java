/*
* PepperoniProtect
* Copyright (C) 2012 EllB <http://www.ellb.net/>
* 
* This program is a part of The SpicyPack and is
* therefore licensed under the SpicyCode custom
* license <http://www.plugins.ellb.net/license/>.
*
*/

package net.ellb.plugins.pepperoniprotect.API;

import java.io.File;
import net.ellb.plugins.PepperoniProtect.Protection.PepperoniArea;
import net.ellb.plugins.pepperoniprotect.Util.PepperoniAreasFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PepperoniAPI {
    public PepperoniAPI() {
        aFile.Load();
    }
    
    PepperoniAreasFile aFile = new PepperoniAreasFile(new File("plugins/PepperoniProtect/areas.yml"));

    public boolean canBuild(Player p) {
        return false;
    }

    public PepperoniArea getCurrentArea(Player p) {
        return null;
    }

    public PepperoniArea getCurrentArea(Location l) {
        for (PepperoniArea a : aFile.getAreas()) {
            
        }
        return null;
    }
}