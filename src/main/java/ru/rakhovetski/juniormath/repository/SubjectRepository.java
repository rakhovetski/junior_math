package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
