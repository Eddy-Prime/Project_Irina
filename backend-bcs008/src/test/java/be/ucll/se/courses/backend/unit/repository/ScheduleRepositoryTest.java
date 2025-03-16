package be.ucll.se.courses.backend.unit.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import be.ucll.se.courses.backend.model.Lecturer;
import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.ScheduleRepository;

@DataJpaTest
public class ScheduleRepositoryTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenScheduleInTheDatabase_whenFindLecturerByUsername_thenUsernameIsReturned() {

        Schedule schedule = mock(Schedule.class);
        Lecturer lecturer = mock(Lecturer.class);
        User user = mock(User.class);

        when(schedule.getLecturer()).thenReturn(lecturer);
        when(lecturer.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("johanp");

        when(scheduleRepository.findByLecturer_User_Username("johanp")).thenReturn(List.of(schedule));

        List<Schedule> schedules = scheduleRepository.findByLecturer_User_Username("johanp");

        assertThat(schedules).isNotNull();
        assertThat(schedules).isNotEmpty();
        assertThat(schedules.get(0).getLecturer().getUser().getUsername()).isEqualTo("johanp");
    }
}
