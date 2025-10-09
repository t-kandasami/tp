package seedu.nustudy.course;

/**
 * Represent a course identified by its name
 */
public class Course {

    /**
     * The name of the course
     */
    private final String courseName;

    /**
     * Create a new Course with the specific name
     *
     * @param courseName The name of the course
     */
    public Course(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Return a string representation of the course
     *
     * @return The course name
     */
    @Override
    public String toString() {
        return courseName;
    }
}
