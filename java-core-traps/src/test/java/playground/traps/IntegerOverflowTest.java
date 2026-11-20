package playground.traps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
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
        // Note that if the argument is equal to the value of Integer#MIN_VALUE, the most negative representable int value,
        // the result is that same value, which is negative.
        // In contrast, the absExact(int) method throws an ArithmeticException for this value.
        assertTrue(absolute < 0);
    }

    @Test
    void multiplyingIntsIntoALong() {
        // Given - a number of days, as an int
        final int days = 30;

        // When - it is converted to milliseconds, into a long
        final long millis = days * 24 * 60 * 60 * 1000;

        // Then - is it 2,592,000,000?
        // Numeric overflow, the value of millis is actually -1702967296.
        // days * 24 * 60 * 60 * 1000 is calculated entirely in int, overflows, and only then widens to long.
        // Starting with a long (30L * ...) fixes it.
        assertEquals(-1702967296L, millis);
    }
}
