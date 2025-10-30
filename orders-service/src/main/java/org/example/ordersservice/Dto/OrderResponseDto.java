package org.example.ordersservice.Dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Integer id;
    private Integer userId;
    private String imeProdukta;
    private Integer quantity;
    private LocalDateTime vremeKupovine;

    private UserDto user;
}
