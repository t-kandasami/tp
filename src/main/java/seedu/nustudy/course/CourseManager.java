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
     * Find a course instance using the course name
     * @param courseName The name of the course
     * @return Course
     */
    public Course findCourse(String courseName) throws NUStudyException {
        for (Course course : courses) {
            if (course.toString().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        throw new NUStudyException("Course not found");
    }

}
