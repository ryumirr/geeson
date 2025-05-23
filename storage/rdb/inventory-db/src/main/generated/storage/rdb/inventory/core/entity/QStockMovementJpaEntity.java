package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStockMovementJpaEntity is a Querydsl query type for StockMovementJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockMovementJpaEntity extends EntityPathBase<StockMovementJpaEntity> {

    private static final long serialVersionUID = 1720237272L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStockMovementJpaEntity stockMovementJpaEntity = new QStockMovementJpaEntity("stockMovementJpaEntity");

    public final StringPath description = createString("description");

    public final QInventoryJpaEntity inventory;

    public final DateTimePath<java.time.LocalDateTime> movementDate = createDateTime("movementDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> movementId = createNumber("movementId", Long.class);

    public final StringPath movementType = createString("movementType");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QStockMovementJpaEntity(String variable) {
        this(StockMovementJpaEntity.class, forVariable(variable), INITS);
    }

    public QStockMovementJpaEntity(Path<? extends StockMovementJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStockMovementJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStockMovementJpaEntity(PathMetadata metadata, PathInits inits) {
        this(StockMovementJpaEntity.class, metadata, inits);
    }

    public QStockMovementJpaEntity(Class<? extends StockMovementJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventory = inits.isInitialized("inventory") ? new QInventoryJpaEntity(forProperty("inventory"), inits.get("inventory")) : null;
    }

}

