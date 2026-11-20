package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
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
        // element '2' was removed by index.
        // The literal 1 is an int, which matches remove(int index) exactly. An exact match beats boxing.
        assertEquals(List.of(1, 3), numberList);
    }

    @Test
    void removeWithAnInteger() {
        // Given - a list of 1, 2 and 3
        final List<Integer> numberList = new ArrayList<>(List.of(1, 2, 3));

        // When - remove(Integer.valueOf(1)) is called
        numberList.remove(Integer.valueOf(1));

        // Then - what does the list hold?
        // the element '1' was removed by value.
        // Integer.valueOf(1) is an object, so remove(Object) is chosen.
        assertEquals(List.of(2, 3), numberList);
    }

}
