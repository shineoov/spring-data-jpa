package shineoov.springdatajpa.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "SampleEntity")
@Table(name = "sample_entity",
        uniqueConstraints = {@UniqueConstraint(name = "uk_email", columnNames = {"email"})},
        indexes = {@Index(name = "ix_type_age", columnList = "type, age")}
)
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(columnDefinition = "varchar(20) default 'Oh'")
    private String firstName;

    @Column(name = "email", nullable = false, length = 50)
    private String accountEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 15)
    private RoleType roleType;

    private Integer age;

    @Transient // db 사용 x
    private Integer number;

    @CreatedDate
    private LocalDateTime createDate;

    @Lob
    private String description;

    public SampleEntity() {
    }

    enum RoleType {
        USER, ADMIN
    }
}
