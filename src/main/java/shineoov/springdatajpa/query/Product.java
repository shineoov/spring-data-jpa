package shineoov.springdatajpa.query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "QueryProduct")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer price;

    private int stockAmount;

}
