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
    basePackages = "storage.rdb.order.repository.adapter",     // ← Order용 Spring Data JPA 인터페이스 패키지
    entityManagerFactoryRef = "orderEntityManagerFactory",
    transactionManagerRef   = "orderTransactionManager"
)
public class OrderDbConfig {

  @Bean("orderDataSourceProperties")
  @ConfigurationProperties("spring.datasource.order")
  public DataSourceProperties orderDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary // ← 기본 DS/EMF/Tx로 쓸 거면 Primary 권장 (선택)
  @Bean("orderDataSource")
  public DataSource orderDataSource(
      @Qualifier("orderDataSourceProperties") DataSourceProperties props) {
    if (props.getDriverClassName() == null || props.getDriverClassName().isBlank()) {
      props.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }
    System.out.println("[OrderDS] url=" + props.getUrl());
    return props.initializeDataSourceBuilder().build();
  }

  @Primary
  @Bean("orderEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(
      @Qualifier("orderDataSource") DataSource ds) {
    var vendor = new HibernateJpaVendorAdapter();
    vendor.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");

    var emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(ds);
    // ✅ 실제 엔티티 패키지 이름에 맞춰주세요
    emf.setPackagesToScan("domain.order"); // 안전하게 상위로
    emf.setJpaVendorAdapter(vendor);
    emf.setPersistenceUnitName("orderPU");
    return emf;
  }

  @Primary
  @Bean("orderTransactionManager")
  public PlatformTransactionManager orderTransactionManager(
      @Qualifier("orderEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
    return new JpaTransactionManager(emf.getObject());
  }
}
