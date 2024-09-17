package ru.users.userservice.service;

import jakarta.validation.constraints.Positive;
import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    List<UserDto> getUsers(String name, String surname, LocalDate registrationDate, PageRequest pageRequest);

    UserDto getById(Integer userId);

    UserDto add(@Valid NewUserDto dto);

    UpdateUserDto update(@Positive Integer userId, @Valid UpdateUserDto dto);

    void removeById(@Positive Integer userId);
}
