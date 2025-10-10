package seedu.nustudy.course;

import java.util.ArrayList;

/**
 * A list to manage all  courses in the application
 */
public class CourseManager {
    private final ArrayList<Course> courses = new ArrayList<>();

    /**
     * Add a course to the list
     *
     * @param course The course to add
     */
    public void add(Course course) {
        courses.add(course);
    }

    /**
     * Get the list of courses
     *
     * @return ArrayList of courses
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }
}
