package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.UserRoom;

public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {
}
