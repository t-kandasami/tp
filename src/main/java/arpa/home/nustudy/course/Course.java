package arpa.home.nustudy.course;

import arpa.home.nustudy.exceptions.NUStudyException;

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

    /**
     * Returns a string representation be stored in data file.
     * The format is "C|Course_Name"
     *
     * @return A string format in the formate "C|Course_Name"
     */
    public String toStorageString() {
        return "C|" + this.courseName;
    }

    /**
     * Converts a stored session in the format {@code "S|COURSE_NAME"} back into
     * a {@code Course} with the course name for loading.
     *
     * @param storageString The stored session string from the data file.
     * @return A {@code Course} which contains the course name.
     * @throws NUStudyException If required format (empty segments between "|") is wrong.
     */
    public static Course fromStorageString(String storageString) throws NUStudyException {
        String[] parts = storageString.split("\\|");

        if (!parts[0].equals("C")) {
            throw new NUStudyException("Invalid course type: " + storageString);
        }
        if (parts.length != 2) {
            throw new NUStudyException("Invalid course format: " + storageString);
        }
        if (parts[1].isEmpty()) {
            throw new NUStudyException("Course name cannot be empty: " + storageString);
        }

        return new Course(parts[1]);
    }
}
