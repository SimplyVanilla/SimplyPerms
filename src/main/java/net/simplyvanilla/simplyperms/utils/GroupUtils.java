package net.simplyvanilla.simplyperms.utils;

import net.simplyvanilla.simplyperms.groups.Group;
import it.fulminazzo.yamlparser.configuration.ConfigurationSection;
import it.fulminazzo.yamlparser.configuration.FileConfiguration;

import java.util.LinkedHashSet;
import java.util.Set;

public class GroupUtils {

    public static Set<Group> loadGroups(final FileConfiguration configuration) {
        final Set<Group> groups = new LinkedHashSet<>();
        final ConfigurationSection groupsSection = configuration.getConfigurationSection("groups");
        if (groupsSection != null) {
            for (final String key : groupsSection.getKeys()) {
                ConfigurationSection groupSection = groupsSection.getConfigurationSection(key);
                if (groupSection != null) groups.add(new Group(groupSection));
            }
        }
        return groups;
    }
}
