package domain.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ShippingAddressJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String formatAddress() {
        return String.format("%s, %s, %s, %s, %s", addressLine1, addressLine2, city, state, postalCode);
    }
}