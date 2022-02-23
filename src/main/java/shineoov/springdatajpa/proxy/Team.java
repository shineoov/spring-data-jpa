package shineoov.springdatajpa.proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ProxyTeam")
@Table(name = "proxy_team")
public class Team {

    @Id
    private String name;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
