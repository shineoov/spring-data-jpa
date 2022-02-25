package shineoov.springdatajpa.query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "QueryTeam")
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
