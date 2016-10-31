package me.faithfull.dao;

import me.faithfull.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Will Faithfull
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
