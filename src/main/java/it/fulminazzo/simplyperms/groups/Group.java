package it.fulminazzo.simplyperms.groups;

import com.velocitypowered.api.permission.Tristate;
import it.fulminazzo.fulmicollection.objects.Printable;
import it.fulminazzo.simplyperms.utils.TristateUtils;
import it.fulminazzo.yamlparser.configuration.ConfigurationSection;
import lombok.Getter;

import java.util.*;

public class Group extends Printable {
    private static final List<Group> GROUPS = new LinkedList<>();
    @Getter
    private final String name;
    final Map<String, Tristate> permissions;

    public Group(final ConfigurationSection groupSection) {
        this.name = groupSection.getName();
        this.permissions = new LinkedHashMap<>();

        for (final String key : groupSection.getKeys()) {
            final String value = groupSection.getString(key);
            this.permissions.put(key.toLowerCase(), TristateUtils.fromString(value));
        }

        GROUPS.add(this);
    }

    public boolean hasPermission(final String permission) {
        return permission != null && this.permissions.getOrDefault(permission, Tristate.UNDEFINED).equals(Tristate.TRUE);
    }

    public Set<String> getPermissions() {
        return this.permissions.keySet();
    }

    public static Group getGroup(final String name) {
        return name == null ? null : getGroups().stream()
                .filter(g -> g.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public static List<Group> getGroups() {
        return new LinkedList<>(GROUPS);
    }

    public static void clearGroups() {
        GROUPS.clear();
    }
}
