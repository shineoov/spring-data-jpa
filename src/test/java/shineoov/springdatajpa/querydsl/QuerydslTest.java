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
import static org.junit.jupiter.api.Assertions.assertAll;
import static shineoov.springdatajpa.querydsl.QMember.*;

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

    @Test
    @DisplayName("검색 조건 - 1")
    void condition_V1() {
        //given
        em.persist(new Member("memberA", 10));
        em.persist(new Member("memberA", 20));
        em.persist(new Member("memberA", 30));
        em.persist(new Member("memberA", 40));

        //when
        QMember member = QMember.member;

        List<Member> memberList = query.selectFrom(member)
                .where(member.username.eq("memberA").and(member.age.goe(30)))
                .orderBy(member.id.desc())
                .fetch();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("검색 조건 - 2")
    void condition_V2() {
        //given
        em.persist(new Member("memberA", 10));
        em.persist(new Member("memberB", 20));
        em.persist(new Member("memberA", 30));
        em.persist(new Member("memberB", 40));

        //when
        QMember member = QMember.member;
        List<Member> memberList = query.selectFrom(member)
//                .where(member.username.contains("ber"))
//                .where(member.username.startsWith("m"))
                .where(member.username.endsWith("B").and(member.age.between(10, 20)))
                .orderBy(member.id.desc())
                .fetch();

        //then
        assertThat(memberList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("페이징 정렬")
    void pagingAndSort() {
        //given
        em.persist(new Member("memberA", 10));
        em.persist(new Member("memberB", 20));
        em.persist(new Member("memberA", 30));

        Member memberToBeFound = new Member("memberB", 40);
        em.persist(memberToBeFound);

        //when
        List<Member> memberList = query.selectFrom(member)
                .orderBy(member.username.desc(), member.id.desc())
                .offset(0).limit(1)
                .fetch();

        //then
        assertAll(
                () -> assertThat(memberList.size()).isEqualTo(1),
                () -> assertThat(memberList.get(0)).isEqualTo(memberToBeFound)
        );
    }
}
