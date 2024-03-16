package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rakhovetski.juniormath.entity.Teacher;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query(value = "SELECT users.* FROM teacher " +
            "JOIN users ON teacher.id = users.id " +
            "WHERE users.username = :username", nativeQuery = true)
    Optional<Teacher> findByUsername(String username);
}
