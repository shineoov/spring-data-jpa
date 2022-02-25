package shineoov.springdatajpa.inheritance.per;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ItemV3 {

    @Id @GeneratedValue
    @Column(name = "item_id")

    private Long id;
    private String name;
    private int price;
}
