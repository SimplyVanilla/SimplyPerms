package net.simplyvanilla.simplyperms.users;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UsersManager {
    private final List<User> users;

    public UsersManager() {
        this.users = new LinkedList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public User getUser(final UUID uniqueId) {
        return uniqueId == null ? null : getUsers().stream()
            .filter(g -> g.getUniqueId().equals(uniqueId))
            .findFirst().orElseGet(() -> new User(uniqueId.toString()));
    }

    public List<User> getUsers() {
        this.users.removeIf(Objects::isNull);
        return new LinkedList<>(this.users);
    }

    public void clearUsers() {
        this.users.clear();
    }
}
