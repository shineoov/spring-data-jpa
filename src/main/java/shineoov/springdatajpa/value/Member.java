package shineoov.springdatajpa.value;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity(name = "EmbeddedMember")
@Table(name = "embedded_member")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOODS", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "member_id"))
    private List<Address> addressHistory = new ArrayList<>();

    @Embedded
    Period workPeriod;

    @Embedded
    Address address;

    public Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, Period workPeriod, Address address) {
        this.name = name;
        this.workPeriod = workPeriod;
        this.address = address;
    }
}
