package me.faithfull.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author Will Faithfull
 */
@Entity
@Getter
@Setter
public class Product extends EntityBase {

    String name;

    long price;

}
