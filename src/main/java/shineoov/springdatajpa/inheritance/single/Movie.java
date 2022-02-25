package shineoov.springdatajpa.inheritance.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SingleMovie")
@DiscriminatorValue("M")
public class Movie extends Item {
    private String director;
    private String actor;
}
