package shineoov.springdatajpa.inheritance.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class Movie extends ItemV2 {

    private String director;
    private String actor;
}
