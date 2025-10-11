package seedu.nustudy.course;

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
    public void add(Course course) {
        courses.add(course);
    }
    
    /**
     * Delete a course from the list
     *
     * @param course The course to delete
     */
    public void delete(Course course) {
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
     * Finds and returns a course with name starting with the given string.
     *
     * @param courseName The prefix of the course being searched.
     * @return The {@code Course} object if found, else {@code null}.
     */
    public Course findCourse(String courseName) {
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(courseName)) {
                return c;
            }
        }
        return null;
    }
}
