package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateUserDto {

    @Size(max = 256, message = "Invalid request format")
    private String name;

    @Size(max = 256, message = "Invalid request format")
    private String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("registration_date")
    private LocalDate registrationDate;
}
