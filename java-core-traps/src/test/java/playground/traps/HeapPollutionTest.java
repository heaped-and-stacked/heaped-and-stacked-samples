package playground.traps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
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
        fail("Write an assertion on 'addingAnInteger'");
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
        fail("Write an assertion on 'readingTheSecondName'");
    }
}
