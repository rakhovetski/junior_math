package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Test;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Integer> {

}
