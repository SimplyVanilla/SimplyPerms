package it.fulminazzo.simplyperms.users;

import it.fulminazzo.simplyperms.GroupedTest;
import it.fulminazzo.simplyperms.groups.Group;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends GroupedTest {

    @Test
    void testGetGroups() {
        User user = new User(UUID.randomUUID().toString());
        user.groups = new HashSet<>();
        user.groups.addAll(Group.getGroups().stream().map(Group::getName).collect(Collectors.toCollection(LinkedHashSet::new)));
        Set<String> expected = new HashSet<>();
        for (int i = 3; i > 0; i--) expected.add("group" + i);
        assertEquals(expected, user.groups);
        assertIterableEquals(expected, user.groups);
    }
}