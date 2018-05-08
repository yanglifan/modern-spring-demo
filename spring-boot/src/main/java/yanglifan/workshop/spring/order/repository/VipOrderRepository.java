package yanglifan.workshop.spring.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import yanglifan.workshop.spring.order.model.Order;

/**
 * @author Yang Lifan
 */
@NoRepositoryBean
public interface VipOrderRepository extends CrudRepository<Order, Long> {
    @Transactional(readOnly = true)
    Order findByOrderCode(String orderCode);

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    Order getByOrderCode(String orderCode);
}
