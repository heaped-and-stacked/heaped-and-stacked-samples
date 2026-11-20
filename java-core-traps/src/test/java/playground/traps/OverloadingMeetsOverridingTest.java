package playground.traps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Replace the fail(...) with assertions in the following tests.
 */
class OverloadingMeetsOverridingTest {

    @Test
    void callingOverloadedAndOverriddenMethods() {
        // Given - an EmailNotifier declared as Notifier, and an EmailNotifier declared as EmailNotifier
        final Notifier notifier = new EmailNotifier();
        final EmailNotifier emailNotifier = new EmailNotifier();

        // When - each calls send with "Hello" and with null
        final String notifierHello = notifier.send("Hello");
        final String emailNotifierHello = emailNotifier.send("Hello");
        final String notifierNull = notifier.send(null);
        final String emailNotifierNull = emailNotifier.send(null);

        // Then - which method runs each time? Each one returns its own name.

        // notifier is declared as Notifier, which only has String send(Object message),
        // so the compiler picks it for both "Hello" and null.
        // At runtime the object is an EmailNotifier, so its override runs.
        assertEquals("EmailNotifier Object", notifierHello);
        assertEquals("EmailNotifier Object", notifierNull);

        // emailNotifier is declared as EmailNotifier, so the compiler can see both String send(Object) and String send(String)
        // and picks the most specific one, also for null.
        assertEquals("EmailNotifier String", emailNotifierHello);
        assertEquals("EmailNotifier String", emailNotifierNull);
    }
}
