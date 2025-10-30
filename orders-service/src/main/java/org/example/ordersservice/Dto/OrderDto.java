package org.example.ordersservice.Dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Integer id;
    private Integer userId;
    private String imeProdukta;
    private Integer quantity;
    private LocalDateTime vremeKupovine;
}
