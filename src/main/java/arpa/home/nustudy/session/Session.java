package arpa.home.nustudy.session;

import arpa.home.nustudy.course.Course;
import arpa.home.nustudy.exceptions.NUStudyException;

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

    /**
     * Converts a stored session in the format {@code "S|COURSE_NAME|LOGGED_HOURS"} back into
     * an {@code Object[]} with the course name and logged session hours for loading.
     *
     * @param storageString The stored session string from the data file.
     * @return An {@code Object[]} where {@code Object[0]} is the course name and {@code Object[1]}
     *     is the logged session hours.
     * @throws NUStudyException If required format (empty segments between "|") is wrong.
     */
    public static Object[] fromStorageString(String storageString) throws NUStudyException {
        String[] parts = storageString.split("\\|");

        if (!parts[0].equals("S")) {
            throw new NUStudyException("Invalid course type: " + storageString);
        }
        if (parts.length != 3) {
            throw new NUStudyException("Invalid session hours format: " + storageString);
        }
        if (parts[1].isEmpty()) {
            throw new NUStudyException("Course name cannot be empty: " + storageString);
        }
        if (parts[2].isEmpty()) {
            throw new NUStudyException("Session hours cannot be empty: " + storageString);
        }

        String courseName = parts[1];
        int hours = Integer.parseInt(parts[2]);

        return new Object[]{courseName, hours};
    }
}
