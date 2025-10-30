package org.example.ordersservice.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Morate uneti userId")
    private Integer userId;

    @NotNull(message = "Morate uneti sta cete kupiti")
    private String imeProdukta;

    @NotNull(message = "Kolicina je obavezna")
    @Min(value = 1, message = "Kolicina mora biti bar 1")
    private Integer quantity;

    @Column(name = "vreme_kupovine", nullable = false)
    private LocalDateTime vremeKupovine = LocalDateTime.now();
}
