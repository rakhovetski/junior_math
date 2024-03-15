package ru.rakhovetski.juniormath.mapper;

import ru.rakhovetski.juniormath.domain.dto.users.UserResponseDto;
import ru.rakhovetski.juniormath.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static List<UserResponseDto> map(List<User> users) {
        return new ArrayList<>(users.stream().map(UserMapper::map).toList());
    }

    public static UserResponseDto map(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getUsername(),
                user.getEmail(),
                user.getLastname(),
                user.getFirstname(),
                user.getPatronymic(),
                user.getRole()
        );
    }
}
