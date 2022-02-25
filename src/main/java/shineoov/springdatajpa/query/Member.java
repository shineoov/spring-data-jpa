package shineoov.springdatajpa.query;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "QueryMember")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    List<Order> orderList = new ArrayList<>();

    public Member() {
    }

    public Member(String username) {
        this.username = username;
    }
}
