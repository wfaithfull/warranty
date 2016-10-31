package me.faithfull.dao;

import me.faithfull.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Will Faithfull
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}
