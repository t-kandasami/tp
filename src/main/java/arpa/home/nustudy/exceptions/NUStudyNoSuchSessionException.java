package arpa.home.nustudy.exceptions;

public class NUStudyNoSuchSessionException extends NUStudyException {
    public NUStudyNoSuchSessionException() {
        super("Please enter a valid session ID");
    }
}
