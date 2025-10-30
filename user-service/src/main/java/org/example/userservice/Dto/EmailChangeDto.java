package org.example.userservice.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailChangeDto {
    @NotBlank(message = "Email morate uneti")
    @Size(min = 3, max = 30)
    @Email
    private String email;
}
