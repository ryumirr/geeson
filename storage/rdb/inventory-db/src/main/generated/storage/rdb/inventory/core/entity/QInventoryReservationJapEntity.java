package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryReservationJapEntity is a Querydsl query type for InventoryReservationJapEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryReservationJapEntity extends EntityPathBase<InventoryReservationJapEntity> {

    private static final long serialVersionUID = -223377939L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryReservationJapEntity inventoryReservationJapEntity = new QInventoryReservationJapEntity("inventoryReservationJapEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final QInventoryJpaEntity inventory;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> reservationId = createNumber("reservationId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> reservedAt = createDateTime("reservedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> reservedQuantity = createNumber("reservedQuantity", Integer.class);

    public final StringPath status = createString("status");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> warehouseId = createNumber("warehouseId", Long.class);

    public QInventoryReservationJapEntity(String variable) {
        this(InventoryReservationJapEntity.class, forVariable(variable), INITS);
    }

    public QInventoryReservationJapEntity(Path<? extends InventoryReservationJapEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryReservationJapEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryReservationJapEntity(PathMetadata metadata, PathInits inits) {
        this(InventoryReservationJapEntity.class, metadata, inits);
    }

    public QInventoryReservationJapEntity(Class<? extends InventoryReservationJapEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventory = inits.isInitialized("inventory") ? new QInventoryJpaEntity(forProperty("inventory"), inits.get("inventory")) : null;
    }

}

