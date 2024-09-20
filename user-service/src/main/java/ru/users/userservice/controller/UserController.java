package ru.users.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @Operation(
            summary = "Получение списка пользователе",
            description = "Параметры: page (Integer) – Номер страницы для пагинации. " +
                    "size (Integer) – Количество записей на странице. " +
                    "name (String) – значение для фильтрация по имени пользователя. " +
                    "surname (String) – значение для фильтрация по фамилии. " +
                    "registration_date (String) – значение для фильтрация по дате."
    )
    public List<UserDto> getUsers(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false) @Size(max = 256, message = "Name cannot exceed 256 characters") String name,
                                  @RequestParam(required = false) @Size(max = 256, message = "Surname cannot exceed 256 characters")String surname,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate registrationDate) {
        log.info("Starting getUsers method. Getting users with params: page={}, size={}, name={}, surname={}, registration_date={}",
                page, size, name, surname, registrationDate);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<UserDto> users = userService.getUsers(name, surname, registrationDate, pageRequest);
        log.info("Completed getUsers method successfully. Results: {}", users);
        return users;
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Получение информации о конкретном пользователе по ID",
            description = "Параметры: user_id (Integer) – Идентификатор пользователя."
    )
    public UserDto getById(@PathVariable @Positive Integer userId) {
        log.info("Starting getById method. Getting user by userId={}", userId);
        UserDto user = userService.getById(userId);
        log.info("Completed getById method successfully. Result: {}", user);
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание нового пользователя",
            description = "Тело запроса: name (String) – Имя пользователя. " +
                    "surname (String) – Фамилия пользователя. " +
                    "registration_date (String) – Дата регистрации."
    )
    public UserDto add(@Valid @RequestBody NewUserDto dto) {
        log.info("Starting add method. Creating user: {}", dto.toString());
        UserDto user = userService.add(dto);
        log.info("Completed add method successfully. Result: {}", user);
        return user;
    }

    @PatchMapping("/{userId}")
    @Operation(
            summary = "Обновление информации о конкретном пользователе",
            description = "Параметры: user_id (Integer) – Идентификатор пользователя. " +
                    "Тело запроса: name (String) – Имя пользователя." +
                    " surname (String) – Фамилия пользователя." +
                    " registration_date (String) – Дата регистрации."
    )
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