package playground.traps;

public class EmailNotifier extends Notifier {
    @Override
    public String send(Object message) {
        return "EmailNotifier Object";
    }

    public String send(String message) {
        return "EmailNotifier String";
    }
}
