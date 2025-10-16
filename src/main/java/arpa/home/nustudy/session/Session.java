package arpa.home.nustudy.session;

import arpa.home.nustudy.course.Course;

/**
 * Represents a course identified by its name
 */
public class Session {
    /**
     * The object of the course
     * <p>
     * The loggedHours for each session
     */
    private final int loggedHours;
    private final Course course;

    /**
     * Creates a new Course with the specific name
     *
     * @param course      The object of the Course
     * @param loggedHours the amount of hours spent
     */
    public Session(final Course course, final int loggedHours) {
        this.loggedHours = loggedHours;
        this.course = course;
    }

    /**
     * Returns a string representation of the course
     *
     * @return The course name
     */
    @Override
    public String toString() {
        return "You have studied for " + loggedHours + "hours" + "for " + course.getCourseName();
    }

    /**
     * Get logged hours for this session
     *
     * @return Logged hours for this session
     */
    public int getLoggedHours() {
        return loggedHours;
    }

    /**
     * Get course for this session
     *
     * @return Course for this session
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Returns a string representation be stored in data file.
     * The format is "S|Course_Name|Logged_Hours"
     *
     * @return A string format in the formate "S|Course_Name|Logged_Hours"
     */
    public String toStorageString() {
        return "S|" + this.course.getCourseName() + "|" + this.loggedHours;
    }
}
