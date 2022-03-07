package shineoov.springdatajpa.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class QuerydslTest {

    @Autowired
    EntityManager em;

    JPAQueryFactory query;


    @BeforeEach
    void setUp() {
        query = new JPAQueryFactory(em);
    }

    @Test
    @DisplayName("QueryDSL 시작")
    void start() {
        // given
        em.persist(new Member("memberA"));
        em.persist(new Member("memberA"));
        em.persist(new Member("memberB"));

        // when
        //QMember qMember = new QMember("m");
        QMember member = QMember.member;

        List<Member> memberList = query
                .select(member).from(member)
                .where(member.username.eq("memberA"))
                .orderBy(member.username.desc())
                .fetch();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }
}
