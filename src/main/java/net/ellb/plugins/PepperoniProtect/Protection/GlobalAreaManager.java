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
import java.util.Set;
import java.util.logging.Level;
import net.ellb.plugins.PepperoniProtect.Bukkit.PepperoniProtect;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class GlobalAreaManager {

    public GlobalAreaManager(PepperoniProtect p) {
        this.plugin = p;
    }
    public PepperoniProtect plugin;
    public Set<PepperoniArea> areas = new HashSet<PepperoniArea>();
    public String[] flagnames = new String[]{
        "location.1.X", "location.1.Y", "location.2.X", "location.2.Y", "location.world", "pvp", "owner", "members", "enter-message", "leave-message", "build.permission", "build.members", "build.otherwise", "market.forsale", "market.cost", "mobs.spawn", "mobs.burn", "move_outside.permission", "move_outside.otherwise", "liquids.lava.permission", "liquids.lava.otherwise", "liquids.water.permission", "liquids.water.otherwise", "potions.throw.permission", "potions.throw.otherwise", "potions.hit.permission", "potions.hit.otherwise", "bowarrow.shoot.permission", "bowarrow.shoot.otherwise", "bowarrow.hit.permission", "bowarrow.hit.otherwise", "enderman_grief", "piston_move"
    };

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

    public void saveArea(PepperoniArea a) {
        YamlConfiguration conf = plugin.getFileManager().getAreasConfig();

    }

    public void addFlag(String key) {
        flagnames[flagnames.length + 1] = key;
    }

    public PepperoniArea createArea(Location L1, Location L2, Player owner) {
        PepperoniArea area = new PepperoniArea(plugin);
        area.create(L1, L2, owner);
        return area;
    }

    public void SaveAreas() {
        try {
            plugin.getFileManager().getAreasConfig().save(plugin.getFileManager().AreasFile);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public void LoadAreas() {
        try {
            plugin.getFileManager().getAreasConfig().load(plugin.getFileManager().AreasFile);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, null, ex);
        }
    }
}