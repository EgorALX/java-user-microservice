package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class NewUserDto {

    @Size(max = 256)
    private String name;

    @Size(max = 256)
    private String surname;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private LocalDate registration_date;
}
