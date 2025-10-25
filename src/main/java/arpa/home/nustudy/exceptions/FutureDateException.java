package arpa.home.nustudy.exceptions;

public class FutureDateException extends NUStudyException {
    public FutureDateException() {
        super("Date cannot be in the future. Please enter a date on or before today.");
    }
}
