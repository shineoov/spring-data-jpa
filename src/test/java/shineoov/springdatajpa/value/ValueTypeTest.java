package shineoov.springdatajpa.value;

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
public class ValueTypeTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("임베디드 타입 저장")
    void saveEmbedded() {

        Address address = new Address("city", "street", "zipcode");
        Period period = new Period(Date.valueOf("2022-03-01"), Date.valueOf("2022-03-30"));
        Member member = new Member("username", period, address);

        // mysql query
        // insert into embedded_member
        // (city, street, zipcode, name, end_date, start_date, id)
        // values ('city', 'street', 'zipcode', 'username', '2022-03-30', '2022-03-01', 3)

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

    @Test
    @DisplayName("컬렉션 타입 저장")
    void saveAndUpdateCollections() {
        Member member = new Member("username");
        member.getFavoriteFoods().add("pizza");
        member.getFavoriteFoods().add("hamburger");
        Address address = new Address("city", "street", "zipcode");
        member.getAddressHistory().add(address);

        // mysql query - 4건
        // insert into embedded_member (city, street, zipcode, name, end_date, start_date, id) values (null, null, null, 'username', null, null, 3)
        // insert into address (member_id, city, street, zipcode) values (3, 'city', 'street', 'zipcode')
        // insert into favorite_foods (member_id, food_name) values (3, 'pizza')
        // insert into favorite_foods (member_id, food_name) values (3, 'hamburger')
        em.persist(member);
        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());

        assertThat(findMember.getFavoriteFoods().contains("pizza")).isTrue();
        assertThat(findMember.getAddressHistory().contains(address)).isTrue();

        // 수정
        findMember.getFavoriteFoods().remove("pizza");
        findMember.getFavoriteFoods().add("pasta");
    }
}
