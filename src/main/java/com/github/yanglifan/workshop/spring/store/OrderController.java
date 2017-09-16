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

import java.util.Optional;
import java.util.concurrent.ExecutionException;
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
    private final MessageService messageService;

    public OrderController(
            OrderRepository orderRepository,
            PassportClient passportClient,
            MessageService messageService) {
        this.orderRepository = orderRepository;
        this.passportClient = passportClient;
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Order create(@RequestHeader("X-User-Token") String userToken, @RequestBody PurchaseRequest purchaseRequest) {
        LOGGER.info("Receive a purchase request {}", purchaseRequest);

        Boolean isValidUser = this.passportClient.isValidUser(userToken);
        if (!isValidUser) {
            throw new InvalidUserException();
        }

        Optional<Order> orderOptional = this.orderRepository.findById(purchaseRequest.getOrderCode());
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }

        Order order = Order.unpaid(purchaseRequest.getOrderCode(), purchaseRequest.getUserId());
        orderRepository.save(order);

        messageService.sendMessage(order);

        return order;
    }

    @RequestMapping(value = "/{orderCode}", method = RequestMethod.GET)
    public Order query(
            @RequestHeader("X-User-Token") String userToken,
            @PathVariable String orderCode) throws ExecutionException, InterruptedException {
        LOGGER.info("Query order by {}", orderCode);
        Boolean isValid = isValidUser(userToken);

        if (!isValid) {
            throw new InvalidUserException();
        }

        return orderRepository.findById(orderCode).get();
    }

    private Boolean isValidUser(String userToken) {
        return passportClient.isValidUser(userToken);
    }

    private Boolean isValidUserAsync(String userToken) throws InterruptedException, ExecutionException {
        Future<ResponseEntity<Boolean>> isValidFuture = passportClient.isValidUserAsync(userToken);

        return isValidFuture.get().getBody();
    }
}
