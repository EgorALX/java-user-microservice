package ru.users.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false, defaultValue = "1") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String surname,
                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate registrationDate) {
        log.info("Starting getUsers method. Getting users with params: page={}, size={}, name={}, surname={}, registration_date={}",
                page, size, name, surname, registrationDate);
        PageRequest pageRequest = PageRequest.of(page, size);
        List<UserDto> users = userService.getUsers(name, surname, registrationDate, pageRequest);
        log.info("Completed getUsers method successfully. Results: {}", users);
        return users;
    }

    @GetMapping("/{user_id}")
    public UserDto getById(@PathVariable @Positive Integer userId) {
        log.info("Starting getById method. Getting user by userId={} ", userId);
        UserDto user = userService.getById(userId);
        log.info("Completed getById method successfully. Result: {}", user);
        return user;

    }

    @PostMapping
    public UserDto add(@RequestBody @Valid NewUserDto dto) {
        log.info("Starting add method. Creating user: {} ", dto.toString());
        UserDto user = userService.add(dto);
        log.info("Completed add method successfully. Result: {}", user);
        return user;
    }

    @PatchMapping("/{user_id}")
    public UpdateUserDto update(@PathVariable @Positive Integer userId,
                                @RequestBody @Valid UpdateUserDto dto) {
        log.info("Starting update method. Updating userId={} ", userId);
        UpdateUserDto user = userService.update(userId, dto);
        log.info("Completed update method successfully. Result: {}", user);
        return user;
    }

    @DeleteMapping("/{user_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> removeById(@PathVariable @Positive Integer userId) {
        log.info("Starting removeById method. removing userId={} ", userId);
        userService.removeById(userId);
        log.info("Completed removeById method successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User successfully deleted");
    }
}
