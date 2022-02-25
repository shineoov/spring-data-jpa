package shineoov.springdatajpa.inheritance.mapped;

import javax.persistence.Entity;

@Entity
public class Member extends BaseEntity {

    private String email;

}
