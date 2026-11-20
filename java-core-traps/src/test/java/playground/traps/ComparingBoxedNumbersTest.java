package playground.traps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Replace the fail(...) with assertions in the following tests.
 */
class ComparingBoxedNumbersTest {

    @Test
    void comparingTwoSmallIdsWithDoubleEquals() {
        // Given - two ids, as an entity would hold them: boxed Longs
        final Long firstId = 127L;
        final Long secondId = 127L;

        // When - they are compared with ==
        final boolean same = firstId == secondId;

        // Then - are they the same?

        // == compares the object references, not the numbers.
        // Java silently rewrites 'Long firstId = 127L' as 'Long firstId = Long.valueOf(127L)'
        // For the range -128 to 127, Java keeps one wrapper object per value and reuses it
        // The comparison will be 'Reference to box 127L is the same as Reference to box 127L' - returns true
        assertTrue(same);
    }

    @Test
    void comparingTwoLargerIdsWithDoubleEquals() {
        // Given - two ids, as an entity would hold them: boxed Longs
        final Long firstId = 128L;
        final Long secondId = 128L;

        // When - they are compared with ==
        final boolean same = firstId == secondId;

        // Then - are they the same?
        // 128 is outside the cache, so each id gets a new primitive wrapper.
        // Two different objects, so == is false, even though both hold 128.
        assertFalse(same);
    }

    @Test
    void comparingTwoLargerIdsWithEquals() {
        // Given - two ids, as an entity would hold them: boxed Longs
        final Long firstId = 128L;
        final Long secondId = 128L;

        // When - they are compared with equals
        final boolean equal = firstId.equals(secondId);

        // Then - are they equal?
        // equals compares the value inside the wrappers, so it’s true for any two Longs that hold the same number.
        assertTrue(equal);
    }
}
