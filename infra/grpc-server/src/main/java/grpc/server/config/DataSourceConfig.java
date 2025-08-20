package grpc.server.config;

// JPA, 트랜잭션 관련
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

// DataSource
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;


@Configuration
public class DataSourceConfig {

    // ===== ORDER DB =====
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "orderEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(orderDataSource())
                .packages("domain.order.entity")
                .persistenceUnit("order")
                .build();
    }

    @Primary
    @Bean(name = "orderTransactionManager")
    public PlatformTransactionManager orderTransactionManager(
            @Qualifier("orderEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    // ===== INVENTORY DB =====
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.inventory")
    public DataSource inventoryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "inventoryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean inventoryEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(inventoryDataSource())
                .packages("domain.inventory.entity")
                .persistenceUnit("inventory")
                .build();
    }

    @Bean(name = "inventoryTransactionManager")
    public PlatformTransactionManager inventoryTransactionManager(
            @Qualifier("inventoryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
