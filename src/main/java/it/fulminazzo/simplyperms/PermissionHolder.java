package it.fulminazzo.simplyperms;

import java.util.Set;

public interface PermissionHolder {

    boolean hasPermission(final String permission);

    Set<String> getPermissions();
}
