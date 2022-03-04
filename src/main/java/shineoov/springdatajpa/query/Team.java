package shineoov.springdatajpa.query;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "QueryTeam")
@NamedQueries({
    @NamedQuery(
            name = "QueryTeam.findByName",
            query = "select t from QueryTeam t where t.name = :teamName"
    ),
    @NamedQuery(
            name = "QueryTeam.count",
            query = "select count(t) from QueryTeam t"
    )
})
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> memberList = new ArrayList<>();

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }
}
