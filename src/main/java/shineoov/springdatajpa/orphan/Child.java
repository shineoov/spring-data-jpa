package shineoov.springdatajpa.orphan;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "OrphanChild")
@Table(name = "orphan_child")
public class Child {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    private Parent parent;

    public Child() {
    }

    public Child(Parent parent) {
        this.parent = parent;
    }
}
