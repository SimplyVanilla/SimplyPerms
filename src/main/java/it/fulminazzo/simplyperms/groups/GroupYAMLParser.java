package it.fulminazzo.simplyperms.groups;

import com.velocitypowered.api.permission.Tristate;
import it.fulminazzo.fulmicollection.interfaces.functions.BiFunctionException;
import it.fulminazzo.fulmicollection.interfaces.functions.TriConsumer;
import it.fulminazzo.yamlparser.configuration.ConfigurationSection;
import it.fulminazzo.yamlparser.configuration.IConfiguration;
import it.fulminazzo.yamlparser.parsers.YAMLParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GroupYAMLParser extends YAMLParser<Group> {

    public GroupYAMLParser() {
        super(Group.class);
    }

    @Override
    protected BiFunctionException<@NotNull IConfiguration, @NotNull String, @Nullable Group> getLoader() {
        return (c, s) -> {
            ConfigurationSection section = c.getConfigurationSection(s);
            if (section == null) return null;
            return new Group(section);
        };
    }

    @Override
    protected TriConsumer<@NotNull IConfiguration, @NotNull String, @Nullable Group> getDumper() {
        return (c, s, g) -> {
            c.set(s, null);
            if (g == null) return;
            s = g.getName();
            ConfigurationSection section = c.createSection(s);
            for (final String perm : g.permissions.keySet()) {
                Tristate tristate = g.permissions.get(perm);
                section.set(perm, tristate.equals(Tristate.TRUE));
            }
        };
    }
}
