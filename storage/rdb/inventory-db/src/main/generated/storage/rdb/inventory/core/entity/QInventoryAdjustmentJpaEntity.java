package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryAdjustmentJpaEntity is a Querydsl query type for InventoryAdjustmentJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryAdjustmentJpaEntity extends EntityPathBase<InventoryAdjustmentJpaEntity> {

    private static final long serialVersionUID = 386257940L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryAdjustmentJpaEntity inventoryAdjustmentJpaEntity = new QInventoryAdjustmentJpaEntity("inventoryAdjustmentJpaEntity");

    public final DateTimePath<java.time.LocalDateTime> adjustedAt = createDateTime("adjustedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> adjustmentId = createNumber("adjustmentId", Long.class);

    public final NumberPath<Integer> adjustmentQuantity = createNumber("adjustmentQuantity", Integer.class);

    public final QInventoryJpaEntity inventory;

    public final StringPath reason = createString("reason");

    public QInventoryAdjustmentJpaEntity(String variable) {
        this(InventoryAdjustmentJpaEntity.class, forVariable(variable), INITS);
    }

    public QInventoryAdjustmentJpaEntity(Path<? extends InventoryAdjustmentJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryAdjustmentJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryAdjustmentJpaEntity(PathMetadata metadata, PathInits inits) {
        this(InventoryAdjustmentJpaEntity.class, metadata, inits);
    }

    public QInventoryAdjustmentJpaEntity(Class<? extends InventoryAdjustmentJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventory = inits.isInitialized("inventory") ? new QInventoryJpaEntity(forProperty("inventory"), inits.get("inventory")) : null;
    }

}

