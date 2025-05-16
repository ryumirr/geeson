package storage.rdb.inventory.core.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWarehouseJpaEntity is a Querydsl query type for WarehouseJpaEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWarehouseJpaEntity extends EntityPathBase<WarehouseJpaEntity> {

    private static final long serialVersionUID = -1249138374L;

    public static final QWarehouseJpaEntity warehouseJpaEntity = new QWarehouseJpaEntity("warehouseJpaEntity");

    public final NumberPath<Integer> capacity = createNumber("capacity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> warehouseId = createNumber("warehouseId", Long.class);

    public QWarehouseJpaEntity(String variable) {
        super(WarehouseJpaEntity.class, forVariable(variable));
    }

    public QWarehouseJpaEntity(Path<? extends WarehouseJpaEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWarehouseJpaEntity(PathMetadata metadata) {
        super(WarehouseJpaEntity.class, metadata);
    }

}

