package shineoov.springdatajpa.cascade;

import javax.persistence.*;

@Entity(name = "CascadeChild")
@Table(name = "cascade_child")
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
