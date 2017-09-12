package com.github.yanglifan.workshop.spring.store;

import lombok.Data;
import lombok.ToString;

/**
 * @author Yang Lifan
 */
@Data
@ToString
public class PurchaseRequest {
    private String orderCode;
    private Long userId;
}
