package shineoov.springdatajpa.proxy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member member = new Member();
        member.setUsername("memberA");
        member.setTeam(teamA);
        em.persist(member);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("데이터베이스 조회 미루기")
    void lazyLoading() {
        Member member2 = em.getReference(Member.class, "memberA"); // 쿼리 실행 X
        member2.getUsername(); // PK 이기 떄문이 쿼리 실행 X
        member2.getTeam(); // 쿼리 실행 O
    }

    @Test
    @DisplayName("프록시 객체 초기화 확인")
    void checkProxy() {
        Member memberA = em.getReference(Member.class, "memberA");

        boolean isLoaded = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(memberA);
        assertThat(isLoaded).isFalse();

        // pk 사용
        memberA.getUsername();

        boolean isLoaded2 = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(memberA);
        assertThat(isLoaded2).isFalse();

        // 객체 사용
        memberA.getTeam();

        boolean isLoaded3 = em.getEntityManagerFactory()
                .getPersistenceUnitUtil().isLoaded(memberA);
        assertThat(isLoaded3).isTrue();
    }

    @Test
    @DisplayName("프록시 객체를 사용하여 효율적으로 저장 예제")
    // @Commit
    void useProxy() {
        Member memberB = new Member("memberB", null);
        em.persist(memberB);

        Team teamA = em.getReference(Team.class, "teamA"); // DB SELECT X
        memberB.setTeam(teamA);
    }
}
