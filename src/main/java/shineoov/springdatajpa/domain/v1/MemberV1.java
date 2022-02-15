package shineoov.springdatajpa.domain.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member_v1")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamV1 team;

    public void setTeam(TeamV1 team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public TeamV1 getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "MemberV1{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
