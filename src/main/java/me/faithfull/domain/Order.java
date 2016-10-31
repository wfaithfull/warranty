package me.faithfull.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * @author Will Faithfull
 */
@Entity
@Getter
@Setter
public class Order extends EntityBase {

    @OneToMany
    Set<Item> items;

}
