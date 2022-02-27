package shineoov.springdatajpa.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Commit
@Transactional
@SpringBootTest
public class JpqlTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("조회하기")
    void query() {
        String jpql = "SELECT m FROM QueryMember AS m WHERE m.username = 'test'";

        TypedQuery<Member> query = em.createQuery(jpql, Member.class);

        // mysql query
        // select member0_.id as id1_23_, member0_.name as name2_23_ from query_member member0_ where member0_.name='test'
        List<Member> memberList = query.getResultList();
    }

    @Test
    @DisplayName("파라미터 바인딩 - 1")
    void parameterBinding_v1() {
        String usernameParam = "test";

        // mysql query
        // select member0_.id as id1_23_, member0_.name as name2_23_ from query_member member0_ where member0_.name='test'
        List<Member> memberList = em.createQuery("SELECT m FROM QueryMember AS m WHERE m.username = :username", Member.class)
                .setParameter("username", usernameParam)
                .getResultList();
    }

    @Test
    @DisplayName("파라미터 바인딩 - 2")
    void parameterBinding_v2() {
        String usernameParam = "test";

        // mysql query
        // select member0_.id as id1_23_, member0_.name as name2_23_ from query_member member0_ where member0_.name='test'
        List<Member> memberList = em.createQuery("SELECT m FROM QueryMember AS m WHERE m.username = ?1", Member.class)
                .setParameter(1, usernameParam)
                .getResultList();
    }

    @Test
    @DisplayName("프로젝션 - 1")
    void projection_v1() {
        //given
        Member memberA = new Member("memberA");
        Member memberB = new Member("memberB");
        em.persist(memberA);
        em.persist(memberB);

        //when
        List<String> memberList = em.createQuery("SELECT m.username FROM QueryMember AS m ", String.class)
                .getResultList();

        //then
        assertThat(memberList).containsExactly(memberA.getUsername(), memberB.getUsername());
    }

    @Test
    @DisplayName("프로젝션 - Embedded")
    void projection_Embedded() {
        //given
        Order order = new Order();
        Address givenAddress = new Address("city", "street", "zipcode");
        order.setAddress(givenAddress);
        em.persist(order);

        //when
        List<Address> addressList = em.createQuery("SELECT o.address FROM QueryOrder AS o", Address.class).getResultList();

        //then
        assertThat(addressList).containsExactly(givenAddress);
    }

    @Test
    @DisplayName("프로젝션 - Scala Type")
    void projection_Scala() {
        //given
        Member memberA = Member.builder().username("memberA").build();
        Member memberB = Member.builder().username("memberB").build();
        em.persist(memberA);
        em.persist(memberB);

        //when
        List<String> usernameList = em.createQuery("SELECT m.username FROM QueryMember AS m ", String.class).getResultList();

        //then
        assertThat(usernameList).containsExactly("memberA", "memberB");
    }

    @Test
    @DisplayName("프로젝션 - dto 로 여러값 조회")
    void projection_MultiValue_withDto() {
        //given
        Member memberA = Member.builder().username("memberA").age(10).build();
        Member memberB = Member.builder().username("memberB").age(20).build();
        em.persist(memberA);
        em.persist(memberB);

        //when
        List<MemberDto> memberDtoList =
                em.createQuery("SELECT new shineoov.springdatajpa.query.MemberDto(m.username, m.age) FROM QueryMember AS m", MemberDto.class)
                        .getResultList();

        //then
        assertThat(memberDtoList).containsExactly(
                new MemberDto("memberA", 10),
                new MemberDto("memberB", 20)
        );
    }

    @Test
    @DisplayName("페이징 쿼리 사용")
    void usePagingQuery() {
        //given
        Member memberA = Member.builder().username("memberA").age(10).build();
        Member memberB = Member.builder().username("memberB").age(20).build();
        Member memberC = Member.builder().username("memberC").age(30).build();
        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);

        //when
        int startPosition = 0;
        int maxResult = 2;
        List<Member> memberList = em.createQuery("SELECT m FROM QueryMember AS m ORDER BY m.age DESC", Member.class)
                .setFirstResult(startPosition)
                .setMaxResults(maxResult)
                .getResultList();

        //then
        assertAll(
                () -> assertThat(memberList.size()).isEqualTo(maxResult),
                () -> assertThat(memberList.get(startPosition)).isEqualTo(memberC)
        );
    }
}
