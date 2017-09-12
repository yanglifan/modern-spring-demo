package com.github.yanglifan.workshop.spring.store;

import com.github.yanglifan.workshop.spring.store.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {
}
