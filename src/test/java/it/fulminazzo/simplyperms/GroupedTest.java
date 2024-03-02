package it.fulminazzo.simplyperms;

import it.fulminazzo.simplyperms.groups.Group;
import it.fulminazzo.simplyperms.utils.GroupUtils;
import it.fulminazzo.yamlparser.configuration.FileConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A test that loads and unloads groups per each iteration.
 */
public abstract class GroupedTest {

    @BeforeEach
    void setUp() {
        loadGroups();
        int size = Group.getGroups().size();
        assertEquals(3, size, String.format("Expected 3 groups but got %s", size));
    }

    @AfterEach
    void tearDown() {
        Group.clearGroups();
        int size = Group.getGroups().size();
        assertEquals(0, size, String.format("Expected 0 groups but got %s", size));
    }

    public static void loadGroups() {
        FileConfiguration configuration = new FileConfiguration("build/resources/test/config.yml");

        GroupUtils.loadGroups(configuration);
    }
}
