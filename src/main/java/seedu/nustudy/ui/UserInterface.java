package seedu.nustudy.ui;

import seedu.nustudy.course.Course;

import java.util.Scanner;

public class UserInterface {
    private static final String LINE_BREAK = "____________________________________________________________";
    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    /**
     * Print a partition line for visual separation
     */
    public static void printLineBreak() {
        System.out.println(LINE_BREAK);
    }

    /**
     * Returns the user's command.
     *
     * @return The trimmed user input command
     */
    public String readCommand() {
        if(INPUT_SCANNER.hasNextLine()) {
            return INPUT_SCANNER.nextLine().trim();
        }
        return null;
    }

    /**
     * Print a success message when a course is added
     *
     * @param course The course that was added
     */
    public static void printCourseAdded(Course course) {
        System.out.println(course + " added");
    }

    public static void printCourseDeleted(Course course) {
        System.out.println(course + " deleted");
    }
}
