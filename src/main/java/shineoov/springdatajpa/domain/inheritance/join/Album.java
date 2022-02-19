package shineoov.springdatajpa.domain.inheritance.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends ItemV2 {

    private String artist;
}
