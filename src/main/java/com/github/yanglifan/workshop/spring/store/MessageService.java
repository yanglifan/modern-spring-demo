package com.github.yanglifan.workshop.spring.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;

/**
 * @author Yang Lifan
 */
@Component
public class MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    @NewSpan
    public void sendMessage(Order order) {
        LOGGER.info("Fake to send a message for the order {}", order.getCode());
    }
}
