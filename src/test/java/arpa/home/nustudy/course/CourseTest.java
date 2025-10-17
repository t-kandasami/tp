package arpa.home.nustudy.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import arpa.home.nustudy.exceptions.NUStudyException;

class CourseTest {

    @Test
    void testStorageStringConversion() {
        Course course = new Course("CS2113");
        assertEquals("C|CS2113", course.toStorageString());
    }

    @Test
    void testStringStorageConversion() throws NUStudyException {
        String storedCourseName = "C|CS2113";
        Course course = Course.fromStorageString(storedCourseName);
        assertEquals("CS2113", course.getCourseName());
    }
}
