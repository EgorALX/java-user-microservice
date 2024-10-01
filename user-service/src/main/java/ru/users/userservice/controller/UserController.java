package ru.users.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.ParamsUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Validated
@Tag(name = "UserController_methods")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Получение информации о пользователях с применением фильтрации")
    public List<UserDto> getUsers(@Valid @ParameterObject ParamsUserDto queryParams) {
        log.info("Starting getUsers method. Getting users with params: {}", queryParams);
        PageRequest pageRequest = queryParams.getPageable();
        List<UserDto> users = userService.getUsers(queryParams.getName(), queryParams.getSurname(),
                queryParams.getRegistrationDate(), pageRequest);
        log.info("Completed getUsers method successfully. Results: {}", users);
        return users;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получение информации о конкретном пользователе по ID")
    public UserDto getById(@PathVariable @Positive Integer userId) {
        log.info("Starting getById method. Getting user by userId={}", userId);
        UserDto user = userService.getById(userId);
        log.info("Completed getById method successfully. Result: {}", user);
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание нового пользователя")
    public UserDto add(@Valid @RequestBody NewUserDto dto) {
        log.info("Starting add method. Creating user: {}", dto.toString());
        UserDto user = userService.add(dto);
        log.info("Completed add method successfully. Result: {}", user);
        return user;
    }

    @PatchMapping("/{userId}")
    @Operation(summary = "Обновление информации о конкретном пользователе",
            description = "Параметр пути: userId (Integer) – Идентификатор пользователя.")
    public UpdateUserDto update(@PathVariable @Positive Integer userId,
                                @Valid @RequestBody UpdateUserDto dto) {
        log.info("Starting update method. Updating userId={}", userId);
        UpdateUserDto user = userService.update(userId, dto);
        log.info("Completed update method successfully. Result: {}", user);
        return user;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Удаление информации о конкретном пользователе по ID",
            description = "Параметры: user_id (Integer) – Идентификатор пользователя"
    )
    public void removeById(@PathVariable @Positive Integer userId) {
        log.info("Starting removeById method. removing userId={}", userId);
        userService.removeById(userId);
        log.info("Completed removeById method successfully");
    }
}