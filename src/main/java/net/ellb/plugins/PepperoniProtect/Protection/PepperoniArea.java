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
import org.bukkit.Location;

public interface PepperoniArea {

    abstract boolean contains(Location loc);

    abstract void setFlag(String flag, Object value);

    abstract int getIntegerFlag(String path);

    abstract boolean getBooleanFlag(String path);

    abstract String getStringFlag(String path);

    abstract List<String> getStringListFlag(String path);
}