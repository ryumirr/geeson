package domain.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "warehouses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WarehouseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long warehouseId;

    private String name;
    private String location;
    private Integer capacity;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private WarehouseJpaEntity(String name, String location, Integer capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static WarehouseJpaEntity create(String name, String location, Integer capacity) {
        return new WarehouseJpaEntity(name, location, capacity);
    }

    public void updateInfo(String name, String location, Integer capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.updatedAt = LocalDateTime.now();
    }
}