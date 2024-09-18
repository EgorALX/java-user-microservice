package ru.users.userservice.mapper;

import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getRegistrationDate());
    }

    public User toUser(NewUserDto dto) {
        return new User(0, dto.getName(), dto.getSurname(), dto.getRegistrationDate());
    }

    public UpdateUserDto toUpdateDto(User user) {
        return new UpdateUserDto(user.getName(), user.getSurname(), user.getRegistrationDate());
    }
}
