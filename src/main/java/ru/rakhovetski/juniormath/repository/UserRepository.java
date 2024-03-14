package ru.rakhovetski.juniormath.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rakhovetski.juniormath.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
