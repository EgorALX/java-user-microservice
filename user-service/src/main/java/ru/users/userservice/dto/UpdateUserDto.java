package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateUserDto {

    private String name;

    private String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("registration_date")
    private LocalDate registrationDate;
}
