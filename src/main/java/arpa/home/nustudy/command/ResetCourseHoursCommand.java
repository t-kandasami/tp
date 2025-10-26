package arpa.home.nustudy.command;

import java.util.logging.Logger;
import java.util.logging.Level;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.utils.ConfirmationHandler;

/**
 * Creates a new ResetCourseHoursCommand with the specified user input. A specific course or all courses can be reset
 * depending on the input.
 */
public class ResetCourseHoursCommand implements Command {
    private static final Logger logger = Logger.getLogger(ResetCourseHoursCommand.class.getName());
    private final String input;

    /**
     * Creates a new ResetCourseHoursCommand with the specified user input. A specific course or all courses can be
     * reset depending on the input.
     *
     * @param input The specified course name or "all" provided by the user.
     */
    public ResetCourseHoursCommand(final String input) {
        this.input = input.trim();
    }

    /**
     * Handles resetting of all course sessions upon double confirmation.
     *
     * @param sessions The {@code SessionManager} instance.
     */
    private static void resetAllCourseHandler(final SessionManager sessions) {
        if (doubleConfirmationWrapper("Are you sure you want to reset hours for ALL courses?", "RESET ALL",
                "reset all courses")) {
            return;
        }

        resetAllCourses(sessions);
        logger.log(Level.SEVERE, "Logged hours for all courses have been reset");
        System.out.println("Logged hours for all courses have been reset");
    }

    /**
     * Wraps first (accepts y/n) and second (accepts safeword) levels of confirmation.
     *
     * @param prompt      The message prompt for first confirmation.
     * @param safeword    The strict safeword required for reset.
     * @param resetAction The reset action upon confirmation.
     *
     * @return {@code true} if reset is cancelled, else {@code false}.
     */
    private static boolean doubleConfirmationWrapper(final String prompt, final String safeword,
            final String resetAction) {
        final boolean confirmed = doubleConfirmation(prompt, safeword, resetAction);

        if (!confirmed) {
            logger.log(Level.INFO, "Reset cancelled");
            System.out.println("Reset cancelled");
            return true;
        }
        return false;
    }

    /**
     * Executes the 2-step confirmation.
     *
     * @param prompt      The message prompt for first confirmation.
     * @param safeword    The strict safeword required for reset.
     * @param resetAction The reset action upon confirmation.
     *
     * @return {@code true} if reset is cancelled, else {@code false}.
     */
    private static boolean doubleConfirmation(final String prompt, final String safeword, final String resetAction) {
        final boolean firstConfirmation = ConfirmationHandler.firstLevelConfirmation(prompt);

        if (!firstConfirmation) {
            return false;
        }

        return ConfirmationHandler.secondLevelConfirmation(safeword, resetAction);
    }

    /**
     * Resets logged hours for all course sessions.
     *
     * @param sessions The {@code SessionManager} instance.
     */
    private static void resetAllCourses(final SessionManager sessions) {
        assert sessions != null : "SessionManager instance cannot be null";
        sessions.clearAllSessions();
        final int clearedCount = sessions.getSessionCount();
        assert clearedCount == 0 : "All sessions should have been cleared by now";
    }

    /**
     * Runs the reset command and checks the input. User confirmation upon reset is done. If input is "all", logged
     * hours for all courses are reset. Else, logged hours for the specified course are reset.
     *
     * @param courses The {@code CourseManager} instance containing all courses.
     *
     * @throws NUStudyException If the specified course is non-existent
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {
        assert courses != null : "CourseManager instance cannot be null";
        assert sessions != null : "SessionManager instance cannot be null";

        if (input.isEmpty()) {
            logger.log(Level.INFO, "Specify a course name or type 'reset all'");
            System.out.println("Specify a course name or type 'reset all'");
            return;
        }

        if ("all".equalsIgnoreCase(input)) {
            resetAllCourseHandler(sessions);
            return;
        }

        resetParticularCourseHandler(courses, sessions);
    }

    /**
     * Indicates whether this command should exit the application
     *
     * @return false, as adding course does not exit the application
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Handles resetting of sessions for provided course upon double confirmation.
     *
     * @param courses The {@code CourseManager} instance.
     *
     * @throws NUStudyNoSuchCourseException If the provided course is non-existent.
     */
    private void resetParticularCourseHandler(final CourseManager courses, final SessionManager sessions)
            throws NUStudyNoSuchCourseException {
        assert courses != null : "CourseManager instance cannot be null";
        assert sessions != null : "SessionManager instance cannot be null";

        final Course target = courses.findCourse(input);

        checkNonExistentCourse(target);

        if (doubleConfirmationWrapper("Are you sure of resetting hours for ", "RESET", "reset hours for " + target)) {
            return;
        }

        sessions.removeAllSessionsForCourse(target);
        logger.log(Level.SEVERE, "Logged hours for " + target + " have been reset");
        System.out.println("Logged hours for " + target + " have been reset");
    }

    /**
     * Confirms that the provided course exists.
     *
     * @param target The course subject to verify.
     *
     * @throws NUStudyNoSuchCourseException If course reference queried is {@code null}.
     */
    private void checkNonExistentCourse(final Course target) throws NUStudyNoSuchCourseException {
        if (target == null) {
            throw new NUStudyNoSuchCourseException(input + " not found");
        }
    }
}
