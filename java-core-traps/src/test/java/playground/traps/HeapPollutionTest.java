package playground.traps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Replace the fail(...) with assertions in the following tests.
 */
class HeapPollutionTest {

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void addingAnIntegerThroughARawType() {
        // Given - a List<String>, and the same list seen through a raw List, as old code would
        final List<String> names = new ArrayList<>(List.of("Alice"));
        final List rawNames = names;

        // When - an Integer is added through the raw list
        final Executable addingAnInteger = () -> rawNames.add(42);

        // Then - does it work, or does it throw? (assertDoesNotThrow or assertThrows)
        // It does not throw, because rawNames does not define a type. This is one of the main reasons to not use raw lists.
        assertDoesNotThrow(addingAnInteger);
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void readingThePollutedElementBack() {
        // Given - a List<String> that an Integer got into through a raw List
        final List<String> names = new ArrayList<>(List.of("Alice"));
        final List rawNames = names;
        rawNames.add(42);

        // When - the second element is read as a String
        final Executable readingTheSecondName = () -> {
            final String secondName = names.get(1);
        };

        // Then - does it work, or does it throw? (assertDoesNotThrow or assertThrows)
        // Throws when trying to read the non string element from the List<String>. Would not throw if we read it from rawNames as object.
        // The compiler inserts a cast to String where names.get(1) is read, and an Integer can't be cast to String.
        assertThrows(ClassCastException.class, readingTheSecondName);
        assertDoesNotThrow(() -> {final Object secondName = rawNames.get(1);});
    }
}
