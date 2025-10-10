package seedu.nustudy.command;

import seedu.nustudy.course.Course;
import seedu.nustudy.course.CourseManager;
import seedu.nustudy.exceptions.NUStudyException;
import seedu.nustudy.exceptions.NUStudyNoSuchCourseException;
import seedu.nustudy.utils.ConfirmationHandler;

/**
 * Creates a new ResetCourseHoursCommand with the specified user input.
 * A specific course or all courses can be reset depending on the input.
 *
 * @ param input The specified course name or "all" provided by the user.
 */
public class ResetCourseHoursCommand implements Command {
	private final String input;

	public ResetCourseHoursCommand(String input) {
		this.input = input.trim();
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
	public void execute(CourseManager courses) throws NUStudyException {

		if (input.isEmpty()) {
			System.out.println("Specify a course name or type 'reset all'");
			return;
		}

		if (input.equalsIgnoreCase("all")) {
			boolean confirmed = ConfirmationHandler.getConfirmation(
					"Are you sure to reset logged hours for all courses?"
			);
			if (!confirmed) {
				System.out.println("Reset cancelled");
				return;
			}

			for (Course c : courses.getCourses()) {
				c.resetHours();
			}
			System.out.println("Logged hours for all courses have been reset");
			return;
		}

		Course target = courses.findCourse(input);
		if (target == null) {
			throw new NUStudyNoSuchCourseException(input + " not found");
		}

		boolean confirmed = ConfirmationHandler.getConfirmation(
				"Are you sure of resetting hours for " + target + "?"
		);
		if (!confirmed) {
			System.out.println("Reset cancelled");
			return;
		}
		target.resetHours();
		System.out.println("Logged hours for " + target + " have been reset");
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
