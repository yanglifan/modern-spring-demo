package com.github.yanglifan.workshop.spring.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author Yang Lifan
 */
@RequestMapping("/orders")
@RestController
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository orderRepository;
    private final PassportClient passportClient;

    public OrderController(OrderRepository orderRepository, PassportClient passportClient) {
        this.orderRepository = orderRepository;
        this.passportClient = passportClient;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Order create(@RequestBody PurchaseRequest purchaseRequest) {
        Order order = this.orderRepository.findOne(purchaseRequest.getOrderCode());
        if (order != null) {
            return order;
        }

        order = new Order(purchaseRequest.getOrderCode(), purchaseRequest.getUserId());
        orderRepository.save(order);

        return order;
    }

    @RequestMapping(value = "/{orderCode}", method = RequestMethod.GET)
    public Order query(
            @RequestHeader("X-User-Token") String userToken,
            @PathVariable String orderCode) throws Exception {

        Future<ResponseEntity<Boolean>> isValidFuture = passportClient.isValidUser(userToken);

        Boolean isValid = isValidFuture.get().getBody();

        if (!isValid) {
            throw new InvalidUserException();
        }

        return orderRepository.findOne(orderCode);
    }
}
