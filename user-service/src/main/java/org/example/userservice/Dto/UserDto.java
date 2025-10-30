package org.example.userservice.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    @NotBlank(message = "Morate uneti ime")
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[A-Z].*",message = "Mora poceti velikim slovom")
    private String firstName;
    @NotBlank(message = "Morate uneti Prezime")
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[A-Z].*",message = "Mora poceti velikim slovom")
    private String lastName;
    @NotBlank(message = "Morate uneti Email")
    @Size(min = 3, max = 25)
    @Email
    private String email;
    @NotBlank(message = "Morate uneti Broj telefona")
    @Size(max = 20)
    private String phone;
}
