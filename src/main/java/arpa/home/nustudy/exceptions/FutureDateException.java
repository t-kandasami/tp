package arpa.home.nustudy.exceptions;

public class FutureDateException extends NUStudyException {
    public FutureDateException(String message) {
        super("Invalid date: " + message + " is in the future. Please provide today's or a past date.");
    }
}
