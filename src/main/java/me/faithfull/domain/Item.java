package me.faithfull.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Will Faithfull
 */
@Entity
@Getter
@Setter
public class Item extends EntityBase {

    @Column(unique = true)
    String serialNumber;

    @ManyToOne
    Product product;

    @OneToOne
    Order order;

}
