package org.example.ordersservice.Dto;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    @NotNull(message = "Morate uneti userId")
    private Integer userId;

    @NotNull(message = "Morate uneti ime proizvoda")
    private String imeProdukta;

    @NotNull(message = "Kolicina je obavezna")
    @Min(value = 1, message = "Kolicina mora biti bar 1")
    private Integer quantity;
}
