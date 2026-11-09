package playground.traps;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class BigDecimalEqualityTest {

    @Test
    void comparingAmountsWithEquals() {
        // Given - the same amount, written with one and with two decimals
        final BigDecimal oneDecimal = new BigDecimal("2.0");
        final BigDecimal twoDecimals = new BigDecimal("2.00");

        // When - they are compared with equals
        final boolean equal = oneDecimal.equals(twoDecimals);

        // Then - are they equal?
        fail("Write an assertion on 'equal'");
    }

    @Test
    void comparingAmountsWithCompareTo() {
        // Given - the same amount, written with one and with two decimals
        final BigDecimal oneDecimal = new BigDecimal("2.0");
        final BigDecimal twoDecimals = new BigDecimal("2.00");

        // When - they are compared with compareTo
        final int comparison = oneDecimal.compareTo(twoDecimals);

        // Then - what does compareTo return?
        fail("Write an assertion on 'comparison'");
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
        fail("Write an assertion on 'size'");
    }

    @Test
    void creatingAnAmountFromADouble() {
        // Given - 0.1, once from a double and once from a String
        final BigDecimal fromDouble = new BigDecimal(0.1);
        final BigDecimal fromString = new BigDecimal("0.1");

        // When - they are compared
        final boolean equal = fromDouble.equals(fromString);

        // Then - are they equal?
        fail("Write an assertion on 'equal'");
    }
}
