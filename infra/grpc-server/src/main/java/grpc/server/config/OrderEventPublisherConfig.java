package grpc.server.config;

import kafka.order.producer.KafkaOrderEventProducer;
import domain.order.message.OrderEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class OrderEventPublisherConfig {

    @Bean
    public OrderEventPublisher orderEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaOrderEventProducer(kafkaTemplate);
    }
}
