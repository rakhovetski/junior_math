package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
