package me.faithfull.dao;

import me.faithfull.domain.Principal;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Will Faithfull
 */
public interface PrincipalRepository extends PagingAndSortingRepository<Principal, String> {

}
