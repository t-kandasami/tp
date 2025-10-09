package seedu.nustudy.command;

import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;

/**
 * Interface for all commands in the application.
 */
public interface Command {

    /**
     * Execute the command
     *
     * @param courses The course list to work with
     * @throws NUStudyException If command execution fails
     */
    void execute(CourseManager courses) throws NUStudyException;

    /**
     * Return whether the command should exit the application
     *
     * @return true if application should exit, false otherwise
     */
    boolean isExit();
}
