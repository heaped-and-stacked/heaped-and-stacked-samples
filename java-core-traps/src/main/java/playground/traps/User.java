package playground.traps;

/**
 * Implements equals and hashCode using only the email field
 */
public class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User other)) {
            return false;
        }
        // Equality of objects is checked only on the 'email' field
        return email.equals(other.email);
    }

    @Override
    public int hashCode() {
        // HashCode checks only the 'email' field
        return email.hashCode();
    }
}