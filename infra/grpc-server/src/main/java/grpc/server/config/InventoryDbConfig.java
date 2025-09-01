package grpc.server.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
    basePackages = "storage.rdb.inventory.repository.adapter",  // ← Inventory용 Spring Data JPA 인터페이스 패키지
    entityManagerFactoryRef = "inventoryEntityManagerFactory",
    transactionManagerRef   = "inventoryTransactionManager"
)
public class InventoryDbConfig {

  @Bean("inventoryDataSourceProperties")
  @ConfigurationProperties("spring.datasource.inventory")
  public DataSourceProperties inventoryDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean("inventoryDataSource")
  public DataSource inventoryDataSource(
      @Qualifier("inventoryDataSourceProperties") DataSourceProperties props) {
    if (props.getDriverClassName() == null || props.getDriverClassName().isBlank()) {
      props.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }
    System.out.println("[InventoryDS] url=" + props.getUrl());
    return props.initializeDataSourceBuilder().build();
  }

  @Bean("inventoryEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean inventoryEntityManagerFactory(
      @Qualifier("inventoryDataSource") DataSource ds) {
    var vendor = new HibernateJpaVendorAdapter();
    vendor.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");

    var emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(ds);
    emf.setPackagesToScan("domain.inventory.domain.entity");
    emf.setJpaVendorAdapter(vendor);
    emf.setPersistenceUnitName("inventoryPU");
    return emf;
  }

  @Bean("inventoryTransactionManager")
  public PlatformTransactionManager inventoryTransactionManager(
      @Qualifier("inventoryEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
    return new JpaTransactionManager(emf.getObject());
  }
}
