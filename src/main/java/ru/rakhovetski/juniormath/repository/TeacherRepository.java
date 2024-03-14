package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Teacher;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}
