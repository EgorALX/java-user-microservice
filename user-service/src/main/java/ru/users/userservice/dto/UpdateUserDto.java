package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateUserDto {

    private String name;

    private String surname;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate registration_date;
}
