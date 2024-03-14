package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
