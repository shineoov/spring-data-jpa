package shineoov.springdatajpa.value;

import org.assertj.core.api.Assertions;
import org.h2.store.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Commit
@Transactional
@SpringBootTest
public class EmbeddedTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("임베디드 타입 저장")
    void save() {

        Address address = new Address("city", "street", "zipcode");
        Period period = new Period(Date.valueOf("2022-03-01"), Date.valueOf("2022-03-30"));
        Member member = new Member("username", period, address);

        em.persist(member);
        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());

        assertAll(
                () -> assertThat(findMember.getName()).isEqualTo("username"),
                () -> assertThat(findMember.getAddress()).isEqualTo(address),
                () -> assertThat(findMember.getWorkPeriod()).isEqualTo(period)
        );
    }
}
