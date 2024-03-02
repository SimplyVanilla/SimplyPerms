package it.fulminazzo.simplyperms.groups;

import it.fulminazzo.simplyperms.GroupedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupTest extends GroupedTest {

    private static Object[][] getGroupTests() {
        return new Object[][]{
                new Object[]{0, "group1"},
                new Object[]{1, "group2"},
                new Object[]{2, "group3"}
        };
    }

    @ParameterizedTest
    @MethodSource("getGroupTests")
    void testGroupWeight(int expected, String groupName) {
        Group group = Group.getGroup(groupName);
        int actual = Group.getWeight(group);
        assertEquals(expected, actual, String.format("Expected weight %s but got %s for group %s",
                expected, actual, group.getName()));
    }
}