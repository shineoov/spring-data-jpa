package shineoov.springdatajpa.composite_key.embedded;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

public class ParentId implements Serializable {

    @Column(name = "parent_id1")
    private String id1;

    @Column(name = "parent_id2")
    private String id2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentId parentId = (ParentId) o;
        return Objects.equals(id1, parentId.id1) && Objects.equals(id2, parentId.id2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id1, id2);
    }
}
