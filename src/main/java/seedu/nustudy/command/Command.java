package seedu.nustudy.command;

import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.session.SessionManager;

/**
 * Interface for all commands in the application.
 */
public interface Command {

    /**
     * Executes the command
     *
     * @param courses The course list to work with
     * @throws NUStudyException If command execution fails
     */
    void execute(CourseManager courses, SessionManager sessions) throws NUStudyException;

    /**
     * Returns whether the command should exit the application
     *
     * @return true if application should exit, false otherwise
     */
    boolean isExit();
}
