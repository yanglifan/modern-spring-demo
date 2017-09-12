package com.github.yanglifan.workshop.spring.store;

import lombok.Data;

/**
 * @author Yang Lifan
 */
@Data
public class PurchaseRequest {
    private String orderCode;
    private Long userId;
}
