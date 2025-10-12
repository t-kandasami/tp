package seedu.nustudy;

import seedu.nustudy.command.Command;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.session.SessionManager;
import seedu.nustudy.ui.UserInterface;
import seedu.nustudy.utils.Parser;

public class NUStudy {
    private static final UserInterface ui = new UserInterface();
    private static final CourseManager courseManager = new CourseManager();
    private static final SessionManager sessionManager = new SessionManager();

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = "NUStudy\n";
        System.out.println("Hello from\n" + logo);

        boolean isExit = false;

        while (!isExit) {
            String userInput = ui.readInput();

            if (userInput == null) {
                break;
            }

            if (userInput.isEmpty()) {
                continue;
            }

            try {
                Command c = Parser.parseCommand(userInput);
                c.execute(courseManager, sessionManager);
            } catch (NUStudyException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
