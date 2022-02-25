package shineoov.springdatajpa.composite_key.embedded;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "ParentEmbedded")
@Table(name = "parent_embedded")
public class Parent {

    @EmbeddedId
    private ParentId id;

    private String name;
}
