package playground.traps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class IntegerOverflowTest {

    @Test
    void takingTheAbsoluteValueOfTheSmallestInt() {
        // Given - the smallest int
        final int smallest = Integer.MIN_VALUE;

        // When - its absolute value is taken
        final int absolute = Math.abs(smallest);

        // Then - is the result positive?
        fail("Write an assertion on 'absolute'");
    }

    @Test
    void multiplyingIntsIntoALong() {
        // Given - a number of days, as an int
        final int days = 30;

        // When - it is converted to milliseconds, into a long
        final long millis = days * 24 * 60 * 60 * 1000;

        // Then - is it 2,592,000,000?
        fail("Write an assertion on 'millis'");
    }
}
