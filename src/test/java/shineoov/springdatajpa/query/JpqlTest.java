package shineoov.springdatajpa.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Commit
@Transactional
@SpringBootTest
public class JpqlTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        em.createQuery("DELETE FROM QueryMember ").executeUpdate();
    }

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
        Member memberA = new Member("memberA");
        Member memberB = new Member("memberB");
        em.persist(memberA);
        em.persist(memberB);

        //when
        List<String> usernameList = em.createQuery("SELECT m.username FROM QueryMember AS m ", String.class).getResultList();

        //then
        assertThat(usernameList).containsExactly(memberA.getUsername(), memberB.getUsername());
    }

    @Test
    @DisplayName("프로젝션 - dto 로 여러값 조회")
    void projection_MultiValue_withDto() {
        //given
        Member memberA = new Member("memberA", 10);
        Member memberB = new Member("memberB", 20);
        em.persist(memberA);
        em.persist(memberB);

        //when
        List<MemberDto> memberDtoList =
                em.createQuery("SELECT new shineoov.springdatajpa.query.MemberDto(m.username, m.age) FROM QueryMember AS m", MemberDto.class)
                        .getResultList();

        //then
        assertThat(memberDtoList).containsExactly(
                new MemberDto(memberA.getUsername(), memberA.getAge()),
                new MemberDto(memberB.getUsername(), memberB.getAge())
        );
    }

    @Test
    @DisplayName("페이징 쿼리 사용")
    void usePagingQuery() {
        //given
        Member memberA = new Member("memberA", 10);
        Member memberB = new Member("memberB", 20);
        Member memberC = new Member("memberC", 30);
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

    @Test
    @DisplayName("집합 함수")
    void setFunction() {
        //given
        em.persist(new Member("memberA", 10));
        em.persist(new Member("memberB", 20));
        em.persist(new Member("memberC", 30));

        //when
        StringBuffer sb = new StringBuffer();
        sb.append("select new shineoov.springdatajpa.query.MemberStatisticsDto(count(m), sum(m.age), avg(m.age), max(m.age), min(m.age))");
        sb.append("from QueryMember AS m");

        MemberStatisticsDto memberStatisticsDto =
                em.createQuery(sb.toString(), MemberStatisticsDto.class).getSingleResult();

        //then
        assertAll(
                () -> assertThat(memberStatisticsDto.getCount()).isEqualTo(3L),
                () -> assertThat(memberStatisticsDto.getSum()).isEqualTo(60L),
                () -> assertThat(memberStatisticsDto.getAvg()).isEqualTo(20.0),
                () -> assertThat(memberStatisticsDto.getMax()).isEqualTo(30),
                () -> assertThat(memberStatisticsDto.getMin()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("group by + having")
    void groupByAndHaving() {
        //given
        em.persist(new Member("memberA", 10));
        em.persist(new Member("memberA", 20));
        em.persist(new Member("memberB", 30));
        em.persist(new Member("memberB", 30));

        //when
        Map<String, Integer> result =
                em.createQuery(
                                "SELECT m.username as username, sum(m.age) as age from QueryMember as m group by m.username ",
                                Tuple.class)
                        .getResultStream()
                        .collect(Collectors.toMap(
                                tuple -> (tuple.get("username").toString()),
                                tuple -> ((Number) tuple.get("age")).intValue()
                        ));

        //then
        assertAll(
                () -> assertThat(result.get("memberA")).isEqualTo(30),
                () -> assertThat(result.get("memberB")).isEqualTo(60)
        );
    }

    @Test
    @DisplayName("inner join")
    void innerJoin() {
        //given
        String teamName = "teamA";
        Team teamA = new Team(teamName);
        em.persist(teamA);

        Member memberA = new Member("userA", 10);
        memberA.setTeam(teamA);
        em.persist(memberA);

        //when
        String query = "SELECT m FROM QueryMember m INNER JOIN m.team t WHERE t.name = :teamName "; // inner 생략 가능
        // mysql query
        // mysql: select member0_.id as id1_23_, member0_.age as age2_23_, member0_.team_id as team_id4_23_, member0_.name as name3_23_
        // from query_member member0_ inner join query_team team1_ on member0_.team_id=team1_.id where team1_.name='teamA'
        List<Member> memberList = em.createQuery(query, Member.class)
                .setParameter("teamName", teamName)
                .getResultList();

        //then
        assertAll(
                () -> assertThat(memberList.size()).isEqualTo(1),
                () -> assertThat(memberList.get(0).getTeam()).isEqualTo(teamA)
        );
    }

    @Test
    @DisplayName("outer join")
    void outerJoin() {
        //given
        Member memberA = new Member("userA", 10);
        em.persist(memberA);

        //when
        // mysql query
        // select member0_.id as id1_23_, member0_.age as age2_23_, member0_.team_id as team_id4_23_, member0_.name as name3_23_
        // from query_member member0_ left outer join query_team team1_ on member0_.team_id=team1_.id
        List<Member> memberList =
                em.createQuery("SELECT m FROM QueryMember m LEFT JOIN m.team t", Member.class).getResultList();

        //then
        assertAll(
                () -> assertThat(memberList.size()).isEqualTo(1),
                () -> assertThat(memberList.get(0).getTeam()).isNull()
        );
    }

    @Test
    @DisplayName("join on")
    void onJoin() {
        //given
        String teamName = "oh";
        Team teamA = new Team(teamName);
        em.persist(teamA);

        Member memberA = new Member("oh", 10);
        memberA.setTeam(teamA);
        em.persist(memberA);

        //when
        // mysql query
        // select member0_.id as id1_23_, member0_.age as age2_23_, member0_.team_id as team_id4_23_, member0_.name as name3_23_
        // from query_member member0_ inner join query_team team1_ on (team1_.name=member0_.name)
        List<Member> memberList = em.createQuery("SELECT m FROM QueryMember m INNER JOIN QueryTeam t ON t.name = m.username ", Member.class)
                .getResultList();

        //then
        assertAll(
                () -> assertThat(memberList.size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("페치 조인")
    void fetchJoin() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member memberA = new Member("userA", 10);
        memberA.setTeam(teamA);
        em.persist(memberA);

        //when
        // mysql query
        // select member0_.id as id1_23_0_, team1_.id as id1_26_1_, member0_.age as age2_23_0_, member0_.team_id as team_id4_23_0_, member0_.name as name3_23_0_, team1_.name as name2_26_1_
        // from query_member member0_ inner join query_team team1_ on member0_.team_id=team1_.id
        List<Member> memberList = em.createQuery("SELECT m FROM QueryMember m JOIN FETCH m.team", Member.class)
                .getResultList();

        //then
        assertAll(
                () -> assertThat(memberList.size()).isEqualTo(1),
                () -> assertThat(memberList.get(0).getTeam()).isEqualTo(teamA)
        );
    }

    @Test
    @DisplayName("컬렉션 페치 조인")
    void fetchJoinAsCollection() {
        //given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member memberA = new Member("userA", 10, teamA);
        Member memberB = new Member("userB", 20, teamA);
        Member memberC = new Member("userC", 30, teamA);
        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);

        em.flush();
        em.clear();

        //when
        //mysql query
        // select team0_.id as id1_26_0_, memberlist1_.id as id1_23_1_, team0_.name as name2_26_0_, memberlist1_.age as age2_23_1_, memberlist1_.team_id as team_id4_23_1_, memberlist1_.name as name3_23_1_, memberlist1_.team_id as team_id4_23_0__, memberlist1_.id as id1_23_0__
        // from query_team team0_ inner join query_member memberlist1_ on team0_.id=memberlist1_.team_id where team0_.name='teamA'
        List<Team> teamList = em.createQuery("SELECT t FROM QueryTeam t JOIN FETCH t.memberList WHERE t.name = :teamName", Team.class)
                .setParameter("teamName", "teamA")
                .getResultList();

        for (Team team : teamList) {
            System.out.println(team.getMemberList());
        }

        //then
        assertAll(
                () -> assertThat(teamList.size()).isEqualTo(3),
                () -> assertThat(teamList.get(0).getMemberList().size()).isEqualTo(3),
                () -> assertThat(teamList.get(1).getMemberList().size()).isEqualTo(3),
                () -> assertThat(teamList.get(2).getMemberList().size()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("서브쿼리")
    void subQuery() {
        //given
        Member memberA = Member.builder().username("teamA").age(20).build();
        Member memberB = Member.builder().username("teamA").age(20).build();
        Member memberC = Member.builder().username("teamA").age(30).build();
        Member memberD = Member.builder().username("teamA").age(30).build();

        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);

        //when
        //mysql query
        // select member0_.id as id1_23_, member0_.age as age2_23_, member0_.team_id as team_id4_23_, member0_.name as name3_23_
        // from query_member member0_ where member0_.age > (select avg(member1_.age) from query_member member1_)
        List<Member> memberList = em.createQuery("select m from QueryMember m where m.age > (select avg(m2.age) from QueryMember  m2)", Member.class)
                .getResultList();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }
}