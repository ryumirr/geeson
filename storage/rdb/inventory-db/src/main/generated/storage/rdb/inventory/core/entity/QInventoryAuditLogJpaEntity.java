package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryAuditLogJpaEntity is a Querydsl query type for InventoryAuditLogJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryAuditLogJpaEntity extends EntityPathBase<InventoryAuditLogJpaEntity> {

    private static final long serialVersionUID = 1858223192L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryAuditLogJpaEntity inventoryAuditLogJpaEntity = new QInventoryAuditLogJpaEntity("inventoryAuditLogJpaEntity");

    public final StringPath action = createString("action");

    public final NumberPath<Long> auditLogId = createNumber("auditLogId", Long.class);

    public final NumberPath<Integer> currentQuantity = createNumber("currentQuantity", Integer.class);

    public final QInventoryJpaEntity inventory;

    public final DateTimePath<java.time.LocalDateTime> performedAt = createDateTime("performedAt", java.time.LocalDateTime.class);

    public final StringPath performedBy = createString("performedBy");

    public final NumberPath<Integer> previousQuantity = createNumber("previousQuantity", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QInventoryAuditLogJpaEntity(String variable) {
        this(InventoryAuditLogJpaEntity.class, forVariable(variable), INITS);
    }

    public QInventoryAuditLogJpaEntity(Path<? extends InventoryAuditLogJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryAuditLogJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryAuditLogJpaEntity(PathMetadata metadata, PathInits inits) {
        this(InventoryAuditLogJpaEntity.class, metadata, inits);
    }

    public QInventoryAuditLogJpaEntity(Class<? extends InventoryAuditLogJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventory = inits.isInitialized("inventory") ? new QInventoryJpaEntity(forProperty("inventory"), inits.get("inventory")) : null;
    }

}

