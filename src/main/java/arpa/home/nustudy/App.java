package arpa.home.nustudy;

import arpa.home.nustudy.command.Command;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.ui.UserInterface;
import arpa.home.nustudy.utils.Parser;

public class App {
    private static final UserInterface ui = new UserInterface();
    private static final CourseManager courseManager = new CourseManager();
    private static final SessionManager sessionManager = new SessionManager();

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        String logo = "App\n";
        System.out.println("Hello from\n" + logo);

        boolean isExit = false;

        while (!isExit) {
            String userInput = UserInterface.readInput();

            if (userInput == null) {
                break;
            }

            if (userInput.isEmpty()) {
                continue;
            }

            try {
                final Command c = Parser.parseCommand(userInput);
                c.execute(App.courseManager, App.sessionManager);
            } catch (final NUStudyException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
