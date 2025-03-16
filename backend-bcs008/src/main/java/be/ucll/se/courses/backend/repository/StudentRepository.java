package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
