package arpa.home.nustudy.course;

/**
 * Represents a course identified by its name
 */
public class Course {
    /**
     * The name of the course
     */
    private final String courseName;
    private int loggedHours;

    /**
     * Creates a new Course with the specific name
     *
     * @param courseName The name of the course
     */
    public Course(final String courseName) {
        this.courseName = courseName;
        loggedHours = 0;
    }

    /**
     * Returns the name of the course
     *
     * @return the name of the course
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Returns the number of study hours logged for this course at call time.
     *
     * @return The number of logged study hours.
     */
    public int getLoggedHours() {
        return loggedHours;
    }

    /**
     * Resets the logged study hours for current course to zero.
     */
    public void resetHours() {
        loggedHours = 0;
    }

    /**
     * Returns a string representation of the course
     *
     * @return The course name
     */
    @Override
    public String toString() {
        return courseName;
    }
}
