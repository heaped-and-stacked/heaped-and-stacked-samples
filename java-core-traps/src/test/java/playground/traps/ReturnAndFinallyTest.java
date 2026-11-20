package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Replace the fail(...) with assertions in the following tests.
 */
class ReturnAndFinallyTest {

    @Test
    @SuppressWarnings("finally")
    void returnInFinally() {
        // Given - a lambda that returns 10 from try and 20 from finally
        final IntSupplier returnFromTryAndReturnFinally = () -> {
            try {
                return 10;
            } finally {
                return 20;
            }
        };

        // When - the lambda is called
        final int result = returnFromTryAndReturnFinally.getAsInt();

        // Then - what does it return?
        // Finally eats up the normal return value
        assertEquals(20, result);
    }

    @Test
    void returnWithFinally() {
        // Given - a lambda that returns 10 from try, and records each step it runs
        final List<String> events = new ArrayList<>();
        final IntSupplier returnFromTryAndFinally = () -> {
            try {
                events.add("try");
                return 10;
            } finally {
                events.add("finally");
            }
        };

        // When - the lambda is called, and the caller records that it got the result back
        final int result = returnFromTryAndFinally.getAsInt();
        events.add("returned");

        // Then - what does it return, and in which order did the steps run?
        // The return value (10) is saved before finally runs, so finally can't change it,
        // but finally still runs before the caller gets the result.
        assertEquals(10, result);
        assertEquals(List.of("try", "finally", "returned"), events);
    }
}
