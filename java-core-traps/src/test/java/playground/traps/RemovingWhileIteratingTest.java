package playground.traps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
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
        // a for-each loop uses an Iterator.
        // emails.remove(...) changes the list's modification count (modCount), which the iterator copied when it was created.
        // On the next next() the iterator sees the count no longer matches its copy and throws.
        assertThrows(ConcurrentModificationException.class, removingOldEmails);
    }

    @Test
    void removingWithRemoveIf() {
        // Given - a list of emails, one of them on a domain we no longer serve
        final List<String> emails = new ArrayList<>(List.of("alice@old.com", "bob@new.com", "carol@new.com"));

        // When - removeIf removes the emails on the old domain
        emails.removeIf(email -> email.endsWith("@old.com"));

        // Then - what does the list hold?
        // removeIf lets the list do the removal itself, so no outside iterator is left out of sync.
        assertEquals(List.of("bob@new.com", "carol@new.com"), emails);
    }
}
