package be.ucll.se.courses.backend.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.ucll.se.courses.backend.model.Course;
import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.model.Student;

public class ScheduleTest {
    private Schedule schedule;
    private Course course;
    private Lecturer lecturer;
    private Student student;

    @BeforeEach
    void setUp() {
        course = new Course("Test Course", "Description", 1, 6);
        lecturer = new Lecturer("Expert in Java", null); // Pass null for user
        student = new Student("r1234567", null); // Pass null for user

        schedule = new Schedule(Instant.now(), Instant.now().plusSeconds(3600), course, lecturer);
    }

    @Test
    void testScheduleCreation() {
        assertNotNull(schedule);
        assertEquals(course, schedule.getCourse());
        assertEquals(lecturer, schedule.getLecturer());
        assertNotNull(schedule.getStart());
        assertNotNull(schedule.getEnd());
    }

    @Test
    void testSettersAndGetters() {
        Instant newStart = Instant.now().plusSeconds(7200);
        Instant newEnd = Instant.now().plusSeconds(10800);
        Course newCourse = new Course("New Course", "New Description", 2, 5);
        Lecturer newLecturer = new Lecturer("New Expert", null);

        schedule.setStart(newStart);
        schedule.setEnd(newEnd);
        schedule.setCourse(newCourse);
        schedule.setLecturer(newLecturer);

        assertEquals(newStart, schedule.getStart());
        assertEquals(newEnd, schedule.getEnd());
        assertEquals(newCourse, schedule.getCourse());
        assertEquals(newLecturer, schedule.getLecturer());
    }

    @Test
    void testAddStudent() {
        assertNotNull(schedule.getStudents());
        assertTrue(schedule.getStudents().isEmpty());

        schedule.addStudent(student);

        assertFalse(schedule.getStudents().isEmpty());
        assertEquals(1, schedule.getStudents().size());
        assertEquals(student, schedule.getStudents().get(0));
    }
}
