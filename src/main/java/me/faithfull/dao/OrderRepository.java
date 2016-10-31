package me.faithfull.dao;

import me.faithfull.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Will Faithfull
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
