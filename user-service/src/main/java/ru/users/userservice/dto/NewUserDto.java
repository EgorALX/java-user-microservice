package ru.users.userservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @NotNull
    @Size(max = 256)
    private String name;

    @NotNull
    @Size(max = 256)
    private String surname;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("registration_date")
    private LocalDate registrationDate;
}
