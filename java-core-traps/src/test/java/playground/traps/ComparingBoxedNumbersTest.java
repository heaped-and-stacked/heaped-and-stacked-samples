package playground.traps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
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
        fail("Write an assertion on 'same'");
    }

    @Test
    void comparingTwoLargerIdsWithDoubleEquals() {
        // Given - two ids, as an entity would hold them: boxed Longs
        final Long firstId = 128L;
        final Long secondId = 128L;

        // When - they are compared with ==
        final boolean same = firstId == secondId;

        // Then - are they the same?
        fail("Write an assertion on 'same'");
    }

    @Test
    void comparingTwoLargerIdsWithEquals() {
        // Given - two ids, as an entity would hold them: boxed Longs
        final Long firstId = 128L;
        final Long secondId = 128L;

        // When - they are compared with equals
        final boolean equal = firstId.equals(secondId);

        // Then - are they equal?
        fail("Write an assertion on 'equal'");
    }
}
