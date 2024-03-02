package it.fulminazzo.simplyperms.users;

import com.velocitypowered.api.permission.Tristate;
import it.fulminazzo.fulmicollection.objects.Printable;
import it.fulminazzo.simplyperms.PermissionHolder;
import it.fulminazzo.simplyperms.groups.Group;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class User extends Printable implements PermissionHolder {
    private static final List<User> USERS = new LinkedList<>();
    private final UUID uniqueId;
    Set<String> groups;

    public User(final String rawId) {
        try {
            this.uniqueId = UUID.fromString(rawId);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("'%s' is not a valid UUID", rawId));
        }

        USERS.add(this);
    }

    @Override
    public boolean hasPermission(final String permission) {
        return getGroups().stream()
                .sorted(Collections.reverseOrder())
                .map(g -> g.getPermissionState(permission))
                .filter(s -> s != Tristate.UNDEFINED)
                .findFirst().orElse(Tristate.FALSE)
                .equals(Tristate.UNDEFINED);
    }

    @Override
    public Set<String> getPermissions() {
        return getGroups().stream().flatMap(g -> g.getPermissions().stream()).collect(Collectors.toSet());
    }

    public Set<Group> getGroups() {
        return this.groups.stream()
                .map(Group::getGroup)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static User getUser(final UUID uniqueId) {
        return uniqueId == null ? null : getUsers().stream()
                .filter(g -> g.getUniqueId().equals(uniqueId))
                .findFirst().orElse(null);
    }

    public static List<User> getUsers() {
        return new LinkedList<>(USERS);
    }

    public static void clearUsers() {
        USERS.clear();
    }
}