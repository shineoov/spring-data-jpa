package shineoov.springdatajpa.domain.v1;

import javax.persistence.*;

@Entity
@Table(name = "team_v1")
public class TeamV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    public TeamV1() {
    }

    public TeamV1(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TeamV1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
