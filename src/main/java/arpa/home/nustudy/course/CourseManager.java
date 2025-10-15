package arpa.home.nustudy.course;

import java.util.ArrayList;

/**
 * A list to manage all  courses in the application
 */
public class CourseManager {
    private final ArrayList<Course> courses = new ArrayList<>();

    /**
     * Adds a course to the list
     *
     * @param course The course to add
     */
    public void add(final Course course) {
        courses.add(course);
    }

    /**
     * Delete a course from the list
     *
     * @param course The course to delete
     */
    public void delete(final Course course) {
        courses.remove(course);
    }

    /**
     * Get the list of courses
     *
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Finds and returns the course whose name is equal to the given string, comparing names case-insensitively.
     *
     * @param courseName The exact match of the course name being searched.
     *
     * @return The {@code Course} object if found, else {@code null}.
     */
    public Course findCourse(final String courseName) {
        for (final Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(courseName)) {
                return c;
            }
        }
        return null;
    }
}
