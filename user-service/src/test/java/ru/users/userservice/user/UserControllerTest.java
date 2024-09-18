package ru.users.userservice.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.users.userservice.controller.UserController;
import ru.users.userservice.dto.NewUserDto;
import ru.users.userservice.dto.UpdateUserDto;
import ru.users.userservice.dto.UserDto;
import ru.users.userservice.service.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private final UserDto userDto = new UserDto(1, "Ivan", "Ivanov",
            LocalDate.of(2020, 1, 1));
    private final UserDto userSecondDto = new UserDto(2, "Van", "Vanov",
            LocalDate.of(2012, 2, 14));

    private final NewUserDto newUserDto = new NewUserDto("Ivan", "Ivanov",
            LocalDate.of(2020, 1, 1));

    private final UpdateUserDto updateUserDto = new UpdateUserDto("Vlad", "ov",
            LocalDate.of(2023, 1, 1));

    @Test
    @SneakyThrows
    void addUserTest() {
        when(userService.add(any(NewUserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.surname", is(userDto.getSurname())))
                .andExpect(jsonPath("$.registration_date", is(userDto.getRegistrationDate().toString())));
    }

    @Test
    @SneakyThrows
    void updateUserTest() {
        when(userService.update(any(Integer.class), any(UpdateUserDto.class))).thenReturn(updateUserDto);

        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(updateUserDto.getName())))
                .andExpect(jsonPath("$.surname", is(updateUserDto.getSurname())))
                .andExpect(jsonPath("$.registration_date", is(updateUserDto.getRegistrationDate().toString())));
    }

    @Test
    @SneakyThrows
    void deleteUserTest() {
        mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    void getByIdTest() {
        when(userService.getById(any(Integer.class))).thenReturn(userDto);

        mockMvc.perform(get("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(userDto.getName())))
                .andExpect(jsonPath("$.surname", is(userDto.getSurname())))
                .andExpect(jsonPath("$.registration_date", is(userDto.getRegistrationDate().toString())));
    }

    @SneakyThrows
    @Test
    void getUsersTest() {
        int page = 1;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        when(userService.getUsers(any(String.class), any(String.class), any(LocalDate.class), any()))
                .thenReturn(List.of(userDto));

        mockMvc.perform(get("/users")
                        .param("name", "Ivan")
                        .param("surname", "Ivanov")
                        .param("registrationDate", String.valueOf(LocalDate.of(2020, 1, 1))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].id", is(userDto.getId()), Integer.class))
                .andExpect(jsonPath("$.[0].name", is(userDto.getName())))
                .andExpect(jsonPath("$.[0].surname", is(userDto.getSurname())))
                .andExpect(jsonPath("$.[0].registration_date", is(userDto.getRegistrationDate().toString())));
    }
}
