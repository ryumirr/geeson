package client.pgClient;

import app.payment.port.TossPaymentRequestPort;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Service
public class TossPaymentRequestAdapter implements TossPaymentRequestPort {
    private final JSONParser parser = new JSONParser();

    @Override
    public JSONObject tossPaymentRequest(String paymentKey, String orderId, BigDecimal amount) throws IOException, ParseException {
        ClassPathResource resource = new ClassPathResource("data/toss_confirm_res.json");
        JSONObject result = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(resource.getInputStream())));
        result.put("orderId", orderId);
        return result;
    }

    @Override
    public JSONObject tossBillingPaymentRequest(String billingKey) throws IOException, ParseException {
        ClassPathResource resource = new ClassPathResource("data/toss_billing_confirm_res.json");
        return (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(resource.getInputStream())));
    }
}
