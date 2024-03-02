package it.fulminazzo.simplyperms.groups;

import com.velocitypowered.api.permission.Tristate;
import it.fulminazzo.fulmicollection.objects.Printable;
import it.fulminazzo.simplyperms.PermissionHolder;
import it.fulminazzo.simplyperms.utils.TristateUtils;
import it.fulminazzo.yamlparser.configuration.ConfigurationSection;
import lombok.Getter;

import java.util.*;

public class Group extends Printable implements PermissionHolder {
    private static final List<Group> GROUPS = new LinkedList<>();
    @Getter
    private final String name;
    final Map<String, Tristate> permissions;

    public Group(final ConfigurationSection groupSection) {
        this.name = groupSection.getName();
        this.permissions = new LinkedHashMap<>();

        for (final String key : groupSection.getKeys()) {
            final String value = groupSection.getString(key);
            this.permissions.put(key.toLowerCase().replace("\\.", "."),
                    TristateUtils.fromString(value));
        }

        GROUPS.add(this);
    }

    @Override
    public boolean hasPermission(final String permission) {
        return permission != null && getPermissionState(permission).equals(Tristate.TRUE);
    }

    public Tristate getPermissionState(final String permission) {
        Tristate tristate = this.permissions.getOrDefault(permission, Tristate.UNDEFINED);
        if (tristate == null) tristate = this.permissions.put(permission, Tristate.UNDEFINED);
        return tristate;
    }

    @Override
    public Set<String> getPermissions() {
        return this.permissions.keySet();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) return this.name.equalsIgnoreCase(((Group) obj).getName());
        return super.equals(obj);
    }

    public static Group getGroup(final String name) {
        return name == null ? null : getGroups().stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public static int getWeight(final Group group) {
        for (int i = 0; i < GROUPS.size(); i++)
            if (GROUPS.get(i).equals(group)) return i;
        return -1;
    }

    public static List<Group> getGroups() {
        return new LinkedList<>(GROUPS);
    }

    public static void clearGroups() {
        GROUPS.clear();
    }
}
