package shineoov.springdatajpa.domain.v1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team_v1")
public class TeamV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")  // 반대쪽 매핑의 필드 이름
    private List<MemberV1> members = new ArrayList<>();

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

    public List<MemberV1> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "TeamV1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
