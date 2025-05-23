package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchaseOrderJpaEntity is a Querydsl query type for PurchaseOrderJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchaseOrderJpaEntity extends EntityPathBase<PurchaseOrderJpaEntity> {

    private static final long serialVersionUID = 938220784L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchaseOrderJpaEntity purchaseOrderJpaEntity = new QPurchaseOrderJpaEntity("purchaseOrderJpaEntity");

    public final DateTimePath<java.time.LocalDateTime> orderDate = createDateTime("orderDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> purchaseOrderId = createNumber("purchaseOrderId", Long.class);

    public final StringPath status = createString("status");

    public final QSupplierJpaEntity supplier;

    public final NumberPath<java.math.BigDecimal> totalAmount = createNumber("totalAmount", java.math.BigDecimal.class);

    public final QWarehouseJpaEntity warehouse;

    public QPurchaseOrderJpaEntity(String variable) {
        this(PurchaseOrderJpaEntity.class, forVariable(variable), INITS);
    }

    public QPurchaseOrderJpaEntity(Path<? extends PurchaseOrderJpaEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchaseOrderJpaEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchaseOrderJpaEntity(PathMetadata metadata, PathInits inits) {
        this(PurchaseOrderJpaEntity.class, metadata, inits);
    }

    public QPurchaseOrderJpaEntity(Class<? extends PurchaseOrderJpaEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.supplier = inits.isInitialized("supplier") ? new QSupplierJpaEntity(forProperty("supplier")) : null;
        this.warehouse = inits.isInitialized("warehouse") ? new QWarehouseJpaEntity(forProperty("warehouse")) : null;
    }

}

