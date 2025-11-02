package arpa.home.nustudy.exceptions;

/**
 * Exception thrown when user inputs an invalid or unrecognized date format. This provides helpful feedback and displays
 * the accepted date formats to the user.
 */
public class WrongDateFormatException extends NUStudyException {

    public WrongDateFormatException(String message) {
        super("Invalid date or date format: " + message + ". Supported formats: yyyy-MM-dd, d/M/yyyy, d-M-yyyy");
    }
}
