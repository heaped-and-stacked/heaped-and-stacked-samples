package playground.traps;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Replace the fail(...) with assertions in the following tests.
 */
class BigDecimalEqualityTest {

    // A BigDecimal is made out of an unscaled integer part and a scale, which is the number of digits after the point

    @Test
    void comparingAmountsWithEquals() {
        // Given - the same amount, written with one and with two decimals
        final BigDecimal oneDecimal = new BigDecimal("2.0");
        final BigDecimal twoDecimals = new BigDecimal("2.00");

        // When - they are compared with equals
        final boolean equal = oneDecimal.equals(twoDecimals);

        // Then - are they equal?

        // Unlike compareTo(BigDecimal) equals considers two BigDecimal
        // objects equal only if they are equal in value and scale.

        // false - 2.0 is not equal to 2.00 because the int part + scale combination is not the same
        // [20, 1] != [200, 2]
        assertFalse(equal);
    }

    @Test
    void comparingAmountsWithCompareTo() {
        // Given - the same amount, written with one and with two decimals
        final BigDecimal oneDecimal = new BigDecimal("2.0");
        final BigDecimal twoDecimals = new BigDecimal("2.00");

        // When - they are compared with compareTo
        final int comparison = oneDecimal.compareTo(twoDecimals);

        // Then - what does compareTo return?
        // 0 - the number of zeroes after the decimal point does not affect the mathematical equality of the number
        // BigDecimal.compareTo(BigDecimal other) returns 0 when the two numbers are equal.
        assertEquals(0, comparison);
    }

    @Test
    void addingTheSameAmountTwiceToASet() {
        // Given - a set of amounts
        final Set<BigDecimal> amounts = new HashSet<>();

        // When - the same amount is added with one and with two decimals
        amounts.add(new BigDecimal("2.0"));
        amounts.add(new BigDecimal("2.00"));

        // Then - how many amounts does the set hold?
        final int size = amounts.size();

        // BigDecimal.equals(BigDecimal) compares both int value and scale
        // [20, 1] is not [200, 2], both get added
        assertEquals(2, size);
    }

    @Test
    void creatingAnAmountFromADouble() {
        // Given - 0.1, once from a double and once from a String
        final BigDecimal fromDouble = new BigDecimal(0.1);
        final BigDecimal fromString = new BigDecimal("0.1");

        // When - they are compared
        final boolean equal = fromDouble.equals(fromString);

        // Then - are they equal?
        // The results of the new BigDecimal(double) constructor can be somewhat unpredictable.
        // One might assume that writing new BigDecimal(0.1) in Java creates a BigDecimal which is exactly equal to 0.1
        // (an unscaled value of 1, with a scale of 1), but it is actually equal to 0.1000000000000000055511151231257827021181583404541015625.
        // This is because 0.1 cannot be represented exactly as a double (or, for that matter, as a binary fraction of any finite length).
        // Thus, the value that is being passed in to the constructor is not exactly equal to 0.1, appearances notwithstanding.
        assertFalse(equal);
    }
}
