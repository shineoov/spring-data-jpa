package shineoov.springdatajpa.inheritance.per;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PerMovie")
@Table(name = "per_movie")
public class Movie extends ItemV3 {
    private String director;
    private String actor;
}
