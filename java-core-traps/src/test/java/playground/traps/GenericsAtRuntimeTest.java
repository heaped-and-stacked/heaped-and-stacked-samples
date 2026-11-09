package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class GenericsAtRuntimeTest {

    @Test
    void listClassesAtRuntime() {
        // Given - a List<String> and a List<Integer>
        final List<String> strings = new ArrayList<>();
        final List<Integer> integers = new ArrayList<>();

        // When - their classes are compared at runtime
        final boolean sameClass = strings.getClass() == integers.getClass();

        // Then - are they the same class?
        fail("Write an assertion on 'sameClass'");
    }
}
