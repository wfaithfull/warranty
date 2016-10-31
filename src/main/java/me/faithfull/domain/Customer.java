package me.faithfull.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author Will Faithfull
 */
@Entity
@Getter
@Setter
public class Customer extends EntityBase {

    String name;

    @OneToMany
    Set<Order> orders;

}
