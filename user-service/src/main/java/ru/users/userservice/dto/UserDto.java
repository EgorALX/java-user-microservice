package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDto {

    private Integer id;

    private String name;

    private String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registration_date;
}
