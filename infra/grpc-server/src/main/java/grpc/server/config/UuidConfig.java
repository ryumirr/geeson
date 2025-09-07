package grpc.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import support.uuid.UuidGenerator;
import uuid.generator.SnowflakeIdGenerator;

@Configuration
public class UuidConfig {
    @Bean
    public UuidGenerator uuidGenerator(@Value("${uuid.node-id}") Long nodeId) {
        return new SnowflakeIdGenerator(nodeId);
    }
}
