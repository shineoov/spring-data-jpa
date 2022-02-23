package shineoov.springdatajpa.cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CascadeParent")
@Table(name = "cascade_parent")
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
