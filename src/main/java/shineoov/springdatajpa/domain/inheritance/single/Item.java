package shineoov.springdatajpa.domain.inheritance.single;

import javax.persistence.*;

@Entity(name = "SingleItem")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

}
