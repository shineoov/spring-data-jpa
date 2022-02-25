package shineoov.springdatajpa.query;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@SpringBootTest
public class QueryLanguageTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("JPQL 로 조회하기 - 1")
    void queryWithJpqlTest() {
        String jpql = "SELECT m FROM QueryMember AS m WHERE m.username = 'test'";

        TypedQuery<Member> query = em.createQuery(jpql, Member.class);

        // mysql query
        // select member0_.id as id1_23_, member0_.name as name2_23_ from query_member member0_ where member0_.name='test'
        List<Member> memberList = query.getResultList();
    }

    @Test
    @DisplayName("Criteria 사용하기")
    void useCriteriaTest() {
        // Criteria 준비
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        //루트 클래스 (조회를 시작할 클래스)
        Root<Member> m = query.from(Member.class);

        // 쿼리 생성
        CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "test"));

        // mysql query
        // select member0_.id as id1_23_, member0_.name as name2_23_ from query_member member0_ where member0_.name='test'
        List<Member> memberList = em.createQuery(cq).getResultList();
    }

}
