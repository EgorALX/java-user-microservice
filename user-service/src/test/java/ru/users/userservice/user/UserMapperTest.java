package ru.users.userservice.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.mapper.UserMapper;
import ru.users.userservice.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;


    @Test
    void toUserDtoTest() {
        User user = new User(1, "Ivan", "Ivanov", LocalDate.of(2023, 1, 1));
        UserDto userDto1 = userMapper.toUserDto(user);

        assertEquals(user.getId(), userDto1.getId());
        assertEquals(user.getName(), userDto1.getName());
        assertEquals(user.getSurname(), userDto1.getSurname());
        assertEquals(user.getRegistrationDate(), userDto1.getRegistrationDate());
    }

    @Test
    void toNewUserTest() {
        NewUserDto userDto = new NewUserDto("Ivan", "Ivanov",
                LocalDate.of(2023, 1, 1));
        User user = userMapper.toUser(userDto);

        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getRegistrationDate(), user.getRegistrationDate());
    }

    @Test
    void toUpdateDtoTest() {
        User user = new User(1, "Ivan", "Ivanov", LocalDate.of(2023, 1, 1));
        UpdateUserDto updateUserDto = userMapper.toUpdateDto(user);

        assertEquals(user.getName(), updateUserDto.getName());
        assertEquals(user.getSurname(), updateUserDto.getSurname());
        assertEquals(user.getRegistrationDate(), updateUserDto.getRegistrationDate());
    }
}
