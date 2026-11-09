package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class ChangingAKeyInASetTest {

    @Test
    void lookingUpAnEqualElement() {
        // Given
        final Set<User> users = new HashSet<>();
        final User alice = new User("Alice", "test_alice@test.com");
        final User anotherAlice = new User("Alice", "test_alice@test.com");

        // When - alice is added to the set
        users.add(alice);

        // Then - is 'anotherAlice' with the same name and email found in the set?
        final boolean containsAnotherAlice = users.contains(anotherAlice);
        fail("Write an assertion on 'containsAnotherAlice'");
    }

    @Test
    void lookingUpAnElementAfterItsKeyChanges() {
        // Given
        final Set<User> users = new HashSet<>();
        final User alice = new User("Alice", "test_alice@test.com");
        final User anotherAlice = new User("Alice", "test_alice@test.com");

        // When - alice, after being added to the set, changes email
        users.add(alice);
        alice.setEmail("test_alice123@test.com");

        // Then - is 'alice' found in the set, or not?
        final boolean containsAlice = users.contains(alice);
        // Then - is 'anotherAlice', with Alice's old email, found in the set?
        final boolean containsAnotherAlice = users.contains(anotherAlice);
        fail("Write assertions on 'containsAlice', 'containsAnotherAlice'");
    }

    @Test
    void addingAnElementAgainAfterItsKeyChanges() {
        // Given
        final Set<User> users = new HashSet<>();
        final User alice = new User("Alice", "test_alice@test.com");

        // When - alice is added to the HashSet, then changes email, then added again
        users.add(alice);
        alice.setEmail("test_alice123@test.com");
        users.add(alice);

        // Then - how many users are in the set, after adding alice twice?
        final int size = users.size();
        fail("Write an assertion on 'size'");
    }
}
