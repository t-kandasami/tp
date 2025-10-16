package arpa.home.nustudy.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void testStorageStringConversion() {
        Course course = new Course("CS2113");
        assertEquals("C|CS2113", course.toStorageString());
    }
}
