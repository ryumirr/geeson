package grpc.server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "domain.inventory.repository",
    entityManagerFactoryRef = "inventoryEntityManagerFactory",
    transactionManagerRef = "inventoryTransactionManager"
)
public class InventoryJpaConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.inventory")
    public DataSource inventoryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean inventoryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("inventoryDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("domain.inventory.entity")
                .persistenceUnit("inventoryPU")
                .build();
    }

    @Bean
    public JpaTransactionManager inventoryTransactionManager(
            @Qualifier("inventoryEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}
