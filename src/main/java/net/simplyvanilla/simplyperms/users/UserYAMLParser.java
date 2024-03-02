package net.simplyvanilla.simplyperms.users;

import it.fulminazzo.fulmicollection.interfaces.functions.BiFunctionException;
import it.fulminazzo.fulmicollection.interfaces.functions.TriConsumer;
import it.fulminazzo.yamlparser.configuration.IConfiguration;
import it.fulminazzo.yamlparser.parsers.YAMLParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class UserYAMLParser extends YAMLParser<User> {

    public UserYAMLParser() {
        super(User.class);
    }

    @Override
    protected BiFunctionException<@NotNull IConfiguration, @NotNull String, @Nullable User> getLoader() {
        return (c, s) -> {
            User user = new User(s);
            Set<String> tmp = c.get(s, HashSet.class);
            if (tmp != null) user.groups.addAll(tmp);
            return user;
        };
    }

    @Override
    protected TriConsumer<@NotNull IConfiguration, @NotNull String, @Nullable User> getDumper() {
        return (c, s, u) -> {
            c.set(s, null);
            if (u == null) return;
            s = u.getUniqueId().toString();
            c.set(s, u.groups);
        };
    }
}
