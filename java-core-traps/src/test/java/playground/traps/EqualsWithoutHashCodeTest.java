package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TODO
 * Replace the fail(...) with assertions in the following tests.
 */
class EqualsWithoutHashCodeTest {

    @Test
    void addingTwoEqualElements() {
        // Given - a set of objects that override equals but not hashCode
        final Set<Book> books = new HashSet<>();

        // When - two equal objects are added to a HashSet (according to the .equals() implementation)
        books.add(new Book("Dune", "fiction"));
        books.add(new Book("Dune", "fiction"));

        // Then - what is the size of the set
        final int size = books.size();
        fail("Write an assertion on 'size'");
    }

    @Test
    void lookingUpAnEqualElement() {
        // Given - a set of objects that override equals but not hashCode
        final Set<Book> books = new HashSet<>();

        // When - a book is added to the HashSet
        books.add(new Book("Dune", "fiction"));

        // Then - does the set contain an equal copy?
        final boolean containsCopy = books.contains(new Book("Dune", "fiction"));
        fail("Write an assertion on 'containsCopy'");
    }
}
