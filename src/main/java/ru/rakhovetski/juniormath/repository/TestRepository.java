package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Room;
import ru.rakhovetski.juniormath.entity.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Integer> {
    Optional<Test> findByRoomAndId(Room room, Integer testId);
}
