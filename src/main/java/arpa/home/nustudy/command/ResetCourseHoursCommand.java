package arpa.home.nustudy.command;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.course.CourseManager;
import arpa.home.nustudy.exceptions.NUStudyException;
import arpa.home.nustudy.exceptions.NUStudyNoSuchCourseException;
import arpa.home.nustudy.session.SessionManager;
import arpa.home.nustudy.utils.ConfirmationHandler;

/**
 * Creates a new ResetCourseHoursCommand with the specified user input.
 * A specific course or all courses can be reset depending on the input.
 * <p>
 *
 * @ param input The specified course name or "all" provided by the user.
 */
public class ResetCourseHoursCommand implements Command {
    private final String input;

    public ResetCourseHoursCommand(final String input) {
        this.input = input.trim();
    }

    /**
     * Handles resetting of all courses upon double confirmation.
     *
     * @param courses The {@code CourseManager} instance.
     */
    private static void resetAllCourseHandler(final CourseManager courses) {
        if (ResetCourseHoursCommand.doubleConfirmationWrapper(
                "Are you sure you want to reset hours for ALL courses?",
                "RESET ALL",
                "reset all courses")) {
            return;
        }

        ResetCourseHoursCommand.resetAllCourses(courses);
        System.out.println("Logged hours for all courses have been reset");
    }

    /**
     * Wraps first (accepts y/n) and second (accepts safeword) levels of confirmation.
     *
     * @param prompt      The message prompt for first confirmation.
     * @param safeword    The strict safeword required for reset.
     * @param resetAction The reset action upon confirmation.
     * @return {@code true} if reset is cancelled, else {@code false}.
     */
    private static boolean doubleConfirmationWrapper(final String prompt, final String safeword, final String resetAction) {
        final boolean confirmed = ResetCourseHoursCommand.doubleConfirmation(prompt, safeword, resetAction);

        if (!confirmed) {
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
     * Resets logged hours for all courses.
     *
     * @param courses The {@code CourseManager} with all courses to reset.
     */
    private static void resetAllCourses(final CourseManager courses) {
        for (final Course c : courses.getCourses()) {
            c.resetHours();
        }
    }

    /**
     * Runs the reset command and checks the input. User confirmation upon reset is done.
     * If input is "all", logged hours for all courses are reset.
     * Else, logged hours for the specified course are reset.
     *
     * @param courses The {@code CourseManager} instance containing all courses.
     * @throws NUStudyException If the specified course is non-existent
     */
    @Override
    public void execute(final CourseManager courses, final SessionManager sessions) throws NUStudyException {

        if (this.input.isEmpty()) {
            System.out.println("Specify a course name or type 'reset all'");
            return;
        }

        if ("all".equalsIgnoreCase(input)) {
            ResetCourseHoursCommand.resetAllCourseHandler(courses);
            return;
        }

        this.resetParticularCourseHandler(courses);
    }

    /**
     * Handles resetting of provided course upon double confirmation.
     *
     * @param courses The {@code CourseManager} instance.
     * @throws NUStudyNoSuchCourseException If the provided course is non-existent.
     */
    private void resetParticularCourseHandler(final CourseManager courses) throws NUStudyNoSuchCourseException {
        final Course target = courses.findCourse(this.input);
        this.checkNonExistentCourse(target);

        if (ResetCourseHoursCommand.doubleConfirmationWrapper(
                "Are you sure of resetting hours for ",
                "RESET",
                "reset hours for " + target)) {
            return;
        }

        target.resetHours();
        System.out.println("Logged hours for " + target + " have been reset");
    }

    /**
     * Confirms that the provided course exists.
     *
     * @param target The course subject to verify.
     * @throws NUStudyNoSuchCourseException If course reference queried is {@code null}.
     */
    private void checkNonExistentCourse(final Course target) throws NUStudyNoSuchCourseException {
        if (target == null) {
            throw new NUStudyNoSuchCourseException(this.input + " not found");
        }
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
}
