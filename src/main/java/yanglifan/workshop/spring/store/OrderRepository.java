package yanglifan.workshop.spring.store;

import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
    @NewSpan
    Order save(Order order);
}
