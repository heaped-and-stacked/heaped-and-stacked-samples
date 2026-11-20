package playground.traps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Replace the fail(...) with assertions in the following tests.
 */
class ListFromArraysAsListTest {

    @Test
    void addingToTheList() {
        // Given - a list made from an array with Arrays.asList
        final String[] array = {"Alice", "Bob"};
        final List<String> names = Arrays.asList(array);

        // When - a name is added to the list
        final Executable addingAName = () -> names.add("Carol");

        // Then - does it work, or does it throw? (assertDoesNotThrow or assertThrows)
        // Arrays.asList() returns a fixed-size view over the array, so add is not supported and throws.
        assertThrows(UnsupportedOperationException.class, addingAName);
    }

    @Test
    void changingTheArrayAfterwards() {
        // Given - a list made from an array with Arrays.asList
        final String[] array = {"Alice", "Bob"};
        final List<String> names = Arrays.asList(array);

        // When - the array changes after the list was made
        array[0] = "Zoe";

        // Then - what is the first name in the list?
        final String firstName = names.getFirst();
        // The list reads straight from the array behind it, so changing the array changes the list.
        assertEquals("Zoe", firstName);
    }
}
