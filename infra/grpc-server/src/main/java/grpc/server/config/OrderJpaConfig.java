package grpc.server.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackages = "domain.order.repository",
    entityManagerFactoryRef = "orderEntityManagerFactory",
    transactionManagerRef = "orderTransactionManager"
)
public class OrderJpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("domain.order.entity") // 엔티티 패키지
                .persistenceUnit("orderPU")
                .build();
    }

    @Bean
    public JpaTransactionManager orderTransactionManager(EntityManagerFactory orderEntityManagerFactory) {
        return new JpaTransactionManager(orderEntityManagerFactory);
    }
}
