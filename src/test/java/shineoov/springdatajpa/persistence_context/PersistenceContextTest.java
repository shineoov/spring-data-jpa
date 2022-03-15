package shineoov.springdatajpa.persistence_context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Transactional
@SpringBootTest
public class PersistenceContextTest {

    @Autowired
    EntityManager em;


    @Test
    @DisplayName("영속성 컨텍스트에 이미 있다면 쿼리 실행 X")
    public void analyzeV1() {

        Member memberA = new Member("memberA");
        // INSERT 쿼리 실행
        em.persist(memberA);

        // 쿼리 실행 X
        Member findMember = em.find(Member.class, memberA.getId());

        log.info("findMember={}", findMember);
    }


    @Test
    @DisplayName("영속성 컨텍스트에 이미 있더라도 JPQL 로 조회하면 쿼리 실행")
    public void analyzeV2() {
        //given
        Member memberA = new Member("memberA");
        em.persist(memberA);
        em.flush();
        em.clear();

        // select member0_.id as id1_21_0_, member0_.name as name2_21_0_ from persistence_member member0_ where member0_.id=1
        em.find(Member.class, memberA.getId());


        // 영속성 컨텍스트에 이미 존재 하지않으면 쿼리 실행
        // select member0_.id as id1_21_, member0_.name as name2_21_ from persistence_member member0_ where member0_.id=1
        em.createQuery("select m from PersistenceMember as m where m.id = :id", Member.class)
                .setParameter("id", memberA.getId())
                .getSingleResult();

        // 쿼리 실행 X
        em.find(Member.class, memberA.getId());
    }
}
