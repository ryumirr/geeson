package kafka.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TossInfraRequest {
    private final ObjectMapper mapper;
    private final ClassPathResource resource ;

    public TossInfraRequest() {
        this.mapper = new ObjectMapper();
        this.resource = new ClassPathResource("data/toss_confirm_res.json");
    }

    public PGConfirmRes pgConfirmRequest() throws IOException {
        return mapper.readValue(resource.getInputStream(), PGConfirmRes.class);
    }
}
