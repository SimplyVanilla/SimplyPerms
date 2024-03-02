package it.fulminazzo.simplyperms.utils;

import com.velocitypowered.api.permission.Tristate;

public class TristateUtils {

    public static Tristate fromString(final String tristate) {
        if (tristate == null) return Tristate.UNDEFINED;
        for (Tristate t : Tristate.values())
            if (t.name().equalsIgnoreCase(tristate)) return t;
        return Tristate.UNDEFINED;
    }
}
