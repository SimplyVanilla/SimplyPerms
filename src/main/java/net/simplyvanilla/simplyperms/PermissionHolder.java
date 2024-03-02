package net.simplyvanilla.simplyperms;

import com.velocitypowered.api.permission.Tristate;

import java.util.Set;

public interface PermissionHolder {

    boolean hasPermission(final String permission);

    Tristate getPermissionState(final String permission);

    Set<String> getPermissions();
}
