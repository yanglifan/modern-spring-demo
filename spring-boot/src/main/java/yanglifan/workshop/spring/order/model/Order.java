package yanglifan.workshop.spring.order.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author Yang Lifan
 */
@Table(name = "qiyue_order")
public class Order {
    @Id
    private Long id;

    @Column(name = "order_code")
    private String orderCode;

    public Order(Long id, String orderCode) {
        this.id = id;
        this.orderCode = orderCode;
    }
}
