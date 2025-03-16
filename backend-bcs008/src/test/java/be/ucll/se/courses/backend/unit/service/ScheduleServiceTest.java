package be.ucll.se.courses.backend.unit.service;

import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.repository.CourseRepository;
import be.ucll.se.courses.backend.repository.LecturerRepository;
import be.ucll.se.courses.backend.repository.ScheduleRepository;
import be.ucll.se.courses.backend.repository.StudentRepository;
import be.ucll.se.courses.backend.service.ScheduleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext(); 
    }

    private void authenticateUser(String username, String role) {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void givenScheduleInTheDatabase_whenGetSchedulesWithAdim_thenAllSchedulesAreReturned() {
        authenticateUser("admin", Role.ADMIN.toGrantedAuthority().getAuthority());
        Schedule schedule = mock(Schedule.class);
        when(scheduleRepository.findAll()).thenReturn(List.of(schedule));

        List<Schedule> schedules = scheduleService.getSchedules(SecurityContextHolder.getContext().getAuthentication());

        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    void givenScheduleInTheDatabase_whenGetSchedulesWithLecturer_thenAllSchedulesForThatLecturerAreReturned() {
        authenticateUser("johanp", Role.LECTURER.toGrantedAuthority().getAuthority());
        Schedule schedule = mock(Schedule.class);
        when(scheduleRepository.findByLecturer_User_Username("johanp")).thenReturn(List.of(schedule));

        List<Schedule> schedules = scheduleService.getSchedules(SecurityContextHolder.getContext().getAuthentication());

        assertNotNull(schedules);
        assertFalse(schedules.isEmpty());
        verify(scheduleRepository, times(1)).findByLecturer_User_Username("johanp");
    }

    @Test
    void givenScheduleInTheDatabase_whenGetSchedulesWithStudent_thenEmptyListIsReturned() {
        authenticateUser("lindas", Role.STUDENT.toGrantedAuthority().getAuthority());

        List<Schedule> schedules = scheduleService.getSchedules(SecurityContextHolder.getContext().getAuthentication());

        assertTrue(schedules.isEmpty());
        verify(scheduleRepository, never()).findAll();
    }
}
