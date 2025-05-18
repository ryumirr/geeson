package app.order;

import org.springframework.stereotype.Service;

@Service
public class FooAppService {
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
