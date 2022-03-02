package shineoov.springdatajpa.query;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "QueryTeam")
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
