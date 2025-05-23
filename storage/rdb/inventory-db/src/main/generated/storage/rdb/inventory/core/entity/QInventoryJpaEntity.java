package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryJpaEntity is a Querydsl query type for InventoryJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryJpaEntity extends EntityPathBase<InventoryJpaEntity> {

    private static final long serialVersionUID = -622254399L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryJpaEntity inventoryJpaEntity = new QInventoryJpaEntity("inventoryJpaEntity");

    public final NumberPath<Integer> availableQuantity = createNumber("availableQuantity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> inventoryId = createNumber("inventoryId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> reorderLevel = createNumber("reorderLevel", Integer.class);

    public final NumberPath<Integer> reorderQuantity = createNumber("reorderQuantity", Integer.class);

    public final NumberPath<Integer> reservedQuantity = createNumber("reservedQuantity", Integer.class);

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QWarehouseJpaEntity warehouse;

    public QInventoryJpaEntity(String variable) {
        this(InventoryJpaEntity.class, forVariable(variable), INITS);
    }

    public QInventoryJpaEntity(Path<? extends InventoryJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryJpaEntity(PathMetadata metadata, PathInits inits) {
        this(InventoryJpaEntity.class, metadata, inits);
    }

    public QInventoryJpaEntity(Class<? extends InventoryJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.warehouse = inits.isInitialized("warehouse") ? new QWarehouseJpaEntity(forProperty("warehouse")) : null;
    }

}

