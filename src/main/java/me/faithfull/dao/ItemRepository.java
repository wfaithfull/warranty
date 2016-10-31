package me.faithfull.dao;

import me.faithfull.domain.Item;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Will Faithfull
 */
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
}
