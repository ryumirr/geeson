package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSupplierJpaEntity is a Querydsl query type for SupplierJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupplierJpaEntity extends EntityPathBase<SupplierJpaEntity> {

    private static final long serialVersionUID = 1878796851L;

    public static final QSupplierJpaEntity supplierJpaEntity = new QSupplierJpaEntity("supplierJpaEntity");

    public final StringPath contactInfo = createString("contactInfo");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> supplierId = createNumber("supplierId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QSupplierJpaEntity(String variable) {
        super(SupplierJpaEntity.class, forVariable(variable));
    }

    public QSupplierJpaEntity(Path<? extends SupplierJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSupplierJpaEntity(PathMetadata metadata) {
        super(SupplierJpaEntity.class, metadata);
    }

}

