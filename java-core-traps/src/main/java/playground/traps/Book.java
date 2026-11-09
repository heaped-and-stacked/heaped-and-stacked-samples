package playground.traps;

import java.util.Objects;

public class Book {
    private final String title;
    private final String genre;


    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        return Objects.equals(title, other.getTitle()) && Objects.equals(genre, other.getGenre());
    }
}
