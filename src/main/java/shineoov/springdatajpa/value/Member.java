package shineoov.springdatajpa.value;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "EmbeddedMember")
@Table(name = "embedded_member")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded Period workPeriod;

    @Embedded Address address;

    public Member() {
    }

    public Member(String name, Period workPeriod, Address address) {
        this.name = name;
        this.workPeriod = workPeriod;
        this.address = address;
    }
}
