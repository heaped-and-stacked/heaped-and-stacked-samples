package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class ListRemoveByIndexOrValueTest {

    @Test
    void removeWithAnInt() {
        // Given - a list of 1, 2 and 3
        final List<Integer> numberList = new ArrayList<>(List.of(1, 2, 3));

        // When - remove(1) is called
        numberList.remove(1);

        // Then - what does the list hold?
        fail("Write an assertion on 'numberList'");
    }

    @Test
    void removeWithAnInteger() {
        // Given - a list of 1, 2 and 3
        final List<Integer> numberList = new ArrayList<>(List.of(1, 2, 3));

        // When - remove(Integer.valueOf(1)) is called
        numberList.remove(Integer.valueOf(1));

        // Then - what does the list hold?
        fail("Write an assertion on 'numberList'");
    }

}
