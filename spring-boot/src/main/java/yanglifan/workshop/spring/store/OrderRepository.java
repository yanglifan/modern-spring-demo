package yanglifan.workshop.spring.store;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
    @NewSpan
    Order save(Order order);

    @Transactional(readOnly = true)
    Order findByCode(String code);

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    Order getByCode(String code);
}
