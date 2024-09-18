package ru.users.userservice.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.users.userservice.UserServiceApplication;
import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.exception.model.NotFoundException;
import ru.users.userservice.mapper.UserMapper;
import ru.users.userservice.model.User;
import ru.users.userservice.repository.UserRepository;
import ru.users.userservice.service.UserServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Transactional
@RequiredArgsConstructor
@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> users;
    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User(1, "Ivan", "Ivanov", LocalDate.of(2023, 1, 1)),
                new User(1, "John", "Smith", LocalDate.of(2023, 1, 1))
        );

        userDto = new UserDto(1, "John", "Smith", LocalDate.of(2023, 1, 1));
        user = new User(1, "John", "Smith", LocalDate.of(2023, 1, 1));
    }

    @Test
    void getUsersTest() {
        when(userRepository.getUsersByParams(any(String.class), any(String.class), any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(users);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);

        List<UserDto> result = userService.getUsers("Ivan", "Ivanov",
                LocalDate.of(2023, 1, 1), PageRequest.of(1, 10));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userDto.getName(), result.get(0).getName());
        assertEquals(userDto.getSurname(), result.get(0).getSurname());
        assertEquals(userDto.getRegistrationDate(), result.get(0).getRegistrationDate());
    }

    @Test
    void getByIdTest() {
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.getById(1);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getSurname(), result.getSurname());
        assertEquals(userDto.getRegistrationDate(), result.getRegistrationDate());
    }

    @Test
    @Transactional
    void addTest() {
        NewUserDto dto = new NewUserDto("New", "Name", LocalDate.now());
        when(userMapper.toUser(dto)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.add(dto);

        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getSurname(), result.getSurname());
        assertEquals(userDto.getRegistrationDate(), result.getRegistrationDate());
    }

    @Test
    @Transactional
    void removeByIdTest() {
        int userId = 1;

        userService.removeById(userId);

        verify(userRepository).deleteById(eq(userId));
    }

    @Test
    @Transactional
    void updateTest() {
        UpdateUserDto dto = new UpdateUserDto("Updated", "Upp", LocalDate.now());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userMapper.toUpdateDto(any(User.class))).thenReturn(dto);

        UpdateUserDto result = userService.update(1, dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertNotNull(result.getSurname());
        assertEquals(dto.getRegistrationDate(), result.getRegistrationDate());
    }

    @Test
    @Transactional
    void updateWithNullTest() {
        UpdateUserDto dto = new UpdateUserDto("Updated", null, LocalDate.now());
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(user));
        when(userMapper.toUpdateDto(any(User.class))).thenReturn(dto);

        UpdateUserDto result = userService.update(1, dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertNull(result.getSurname());
        assertEquals(dto.getRegistrationDate(), result.getRegistrationDate());
    }

    @Test
    void getByIdWithIncorrectIdTest() {
        assertThrows(NotFoundException.class, () -> userService.getById(100));
    }

    @Test
    void updateWithIncorrectIdTest() {
        UpdateUserDto dto = new UpdateUserDto("Updated", "Upp", LocalDate.now());
        assertThrows(NotFoundException.class, () -> userService.update(100, dto));
    }
}
