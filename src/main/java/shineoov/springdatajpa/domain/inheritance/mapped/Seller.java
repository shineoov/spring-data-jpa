package shineoov.springdatajpa.domain.inheritance.mapped;

import javax.persistence.Entity;

@Entity
public class Seller extends BaseEntity {

    private String shopName;
}
