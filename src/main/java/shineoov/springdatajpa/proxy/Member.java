package shineoov.springdatajpa.proxy;

import javax.persistence.*;

@Entity(name = "ProxyMember")
@Table(name = "proxy_member")
public class Member {

    @Id
    private String username;

    @ManyToOne
    private Team team;

    public Member() {
    }

    public Member(String username, Team team) {
        this.username = username;
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public Team getTeam() {
        return team;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
