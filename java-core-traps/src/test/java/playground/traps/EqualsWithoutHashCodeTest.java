package playground.traps;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
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
        // Hashcode is not overridden in Book, so the implementation from Object is used,
        // which assigns a new hash to each object created
        // The 2 created objects in our test will not have the same hashcode, so both elements will be added to the set
        assertEquals(2, size);
    }

    @Test
    void lookingUpAnEqualElement() {
        // Given - a set of objects that override equals but not hashCode
        final Set<Book> books = new HashSet<>();

        // When - a book is added to the HashSet
        books.add(new Book("Dune", "fiction"));

        // Then - does the set contain an equal copy?
        final boolean containsCopy = books.contains(new Book("Dune", "fiction"));

        // The hashcode of the copy does not match the hashcode of the element in the set, so equals() is never checked
        assertFalse(containsCopy);
    }
}
