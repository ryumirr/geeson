package api.order.controller;

import api.order.request.RegisterCustomerReq;
import api.order.response.RegisterCustomerRes;
import app.order.app.CustomerRegisterApp;
import app.order.command.CustomerRegisterCommand;
import domain.order.domain.entity.CustomerJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerApi {
    private final CustomerRegisterApp customerRegisterApp;

    @PostMapping("/")
    public RegisterCustomerRes createCustomer(
        @RequestBody RegisterCustomerReq registerCustomerReq
    ){
        CustomerJpaEntity customerJpaEntity = customerRegisterApp.registerCustomer(new CustomerRegisterCommand(
            registerCustomerReq.name(),
            registerCustomerReq.email(),
            registerCustomerReq.phone()
        ));
        return new RegisterCustomerRes(
            customerJpaEntity.getCustomerId(),
            customerJpaEntity.getName(),
            customerJpaEntity.getEmail(),
            customerJpaEntity.getPhone()
        );
    }
}
