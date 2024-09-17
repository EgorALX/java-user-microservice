package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDto {

    private Integer id;

    private String name;

    private String surname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate registration_date;
}
