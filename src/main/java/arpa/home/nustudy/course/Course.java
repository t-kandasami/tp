package arpa.home.nustudy.course;

/**
 * Represents a course identified by its name
 */
public class Course {
    /**
     * The name of the course
     */
    private final String courseName;

    /**
     * Creates a new Course with the specific name
     *
     * @param courseName The name of the course
     */
    public Course(final String courseName) {
        this.courseName = courseName;
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
     * Returns a string representation of the course
     *
     * @return The course name
     */
    @Override
    public String toString() {
        return courseName;
    }
}
