package playground.traps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class RemovingWhileIteratingTest {

    @Test
    void removingInsideAForEachLoop() {
        // Given - a list of emails, one of them on a domain we no longer serve
        final List<String> emails = new ArrayList<>(List.of("alice@old.com", "bob@new.com", "carol@new.com"));

        // When - a for-each loop removes the emails on the old domain
        final Executable removingOldEmails = () -> {
            for (final String email : emails) {
                if (email.endsWith("@old.com")) {
                    emails.remove(email);
                }
            }
        };

        // Then - does it work, or does it throw? (assertDoesNotThrow or assertThrows)
        fail("Write an assertion on 'removingOldEmails'");
    }

    @Test
    void removingWithRemoveIf() {
        // Given - a list of emails, one of them on a domain we no longer serve
        final List<String> emails = new ArrayList<>(List.of("alice@old.com", "bob@new.com", "carol@new.com"));

        // When - removeIf removes the emails on the old domain
        emails.removeIf(email -> email.endsWith("@old.com"));

        // Then - what does the list hold?
        fail("Write an assertion on 'emails'");
    }
}
