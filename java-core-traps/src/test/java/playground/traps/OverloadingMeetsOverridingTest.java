package playground.traps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
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
        fail("Write assertions on 'notifierHello', 'emailNotifierHello', 'notifierNull', 'emailNotifierNull'");
    }
}
