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
    @Column(name = "member_id")
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamV1 team;

    public void setTeam(TeamV1 team) {
        // 반대방향 객체 그래프 탐색이 가능하도록
        if(this.team != null) {
            this.team.getMembers().remove(this);
        }
        this.team = team;
        team.getMembers().add(this);
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
