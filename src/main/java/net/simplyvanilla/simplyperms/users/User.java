package net.simplyvanilla.simplyperms.users;

import com.velocitypowered.api.permission.Tristate;
import it.fulminazzo.fulmicollection.objects.Printable;
import lombok.Getter;
import net.simplyvanilla.simplyperms.PermissionHolder;
import net.simplyvanilla.simplyperms.groups.Group;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class User extends Printable implements PermissionHolder {
    private final UUID uniqueId;
    Set<String> groups = new LinkedHashSet<>();

    User(final String rawId) {
        try {
            this.uniqueId = UUID.fromString(rawId);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("'%s' is not a valid UUID", rawId));
        }
    }

    @Override
    public boolean hasPermission(final String permission) {
        return getPermissionState(permission).equals(Tristate.TRUE);
    }

    @Override
    public Tristate getPermissionState(String permission) {
        return getGroups().stream()
                .map(g -> g.getPermissionState(permission))
                .findFirst().orElse(Tristate.UNDEFINED);
    }

    @Override
    public Set<String> getPermissions() {
        return getGroups().stream().flatMap(g -> g.getPermissions().stream()).collect(Collectors.toSet());
    }

    /**
     * Returns the corresponding set of groups of the user,
     * sorted from higher to lower weight.
     *
     * @return the groups
     */
    public Set<Group> getGroups() {
        this.groups.add("default");
        return this.groups.stream()
                .map(Group::getGroup)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(g -> -Group.getWeight(g)))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
