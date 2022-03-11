package shineoov.springdatajpa.querydsl;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static shineoov.springdatajpa.querydsl.QMember.member;
import static shineoov.springdatajpa.querydsl.QOrder.order;
import static shineoov.springdatajpa.querydsl.QOrderItem.orderItem;

@Commit
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

    @Test
    @DisplayName("basic join")
    void join() {
        //given
        Member memberA = new Member("memberA", 10);
        em.persist(memberA);

        Order order = new Order("orderName", memberA);
        em.persist(order);

        OrderItem orderItem = new OrderItem(order);
        em.persist(orderItem);

        em.flush();
        em.clear();

        //when
//      SELECT order0_.id AS id1_24_, order0_.member_id AS member_i3_24_,order0_.name AS name2_24_
//      FROM query_dsl_order order0_
//      INNER JOIN query_dsl_member member1_ ON order0_.member_id = member1_.id
//      LEFT OUTER JOIN query_dsl_order_item orderitem2_ ON order0_.id = orderitem2_.order_id
        List<Order> orderList = query.select(QOrder.order)
                .from(QOrder.order)
                .join(QOrder.order.member)
                .leftJoin(QOrder.order.orderItemList)
                .fetch();

        //then
        Order firstOrder = orderList.get(0);
        assertAll(
                () -> assertThat(firstOrder).isEqualTo(order),
                () -> assertThat(firstOrder.getMember()).isEqualTo(memberA),
                () -> assertThat(firstOrder.getOrderItemList().get(0)).isEqualTo(orderItem)
        );
    }

    @Test
    @DisplayName("join on 절")
    void joinOn() {

        /*
            SELECT order0_.id AS id1_24_, order0_.member_id AS member_i3_24_, order0_.name AS name2_24_
            FROM query_dsl_order order0_
            LEFT OUTER JOIN query_dsl_order_item orderiteml1_ ON order0_.id = orderiteml1_.order_id
            and(orderiteml1_.name = orderiteml1_.name)
        */
        query.selectFrom(order)
                .leftJoin(order.orderItemList, orderItem)
                .on(orderItem.name.eq(orderItem.name))
                .fetch();
    }

    @Test
    @DisplayName("join fetch")
    void joinFetch() {
        /*
        SELECT
            order0_.id AS id1_24_0_,
            member1_.id AS id1_23_1_,
            orderiteml2_.id AS id1_25_2_,
            order0_.member_id AS member_i3_24_0_,
            order0_.name AS name2_24_0_,
            member1_.age AS age2_23_1_,
            member1_.username AS username3_23_1_,
            orderiteml2_.name AS name2_25_2_,
            orderiteml2_.order_id AS order_id3_25_2_,
            orderiteml2_.order_id AS order_id3_25_0__,
            orderiteml2_.id AS id1_25_0__
        FROM query_dsl_order order0_
        INNER JOIN query_dsl_member member1_ ON order0_.member_id = member1_.id
        LEFT OUTER JOIN query_dsl_order_item orderiteml2_ ON order0_.id = orderiteml2_.order_id
         */
        List<Order> orderList = query.selectFrom(order)
                .innerJoin(order.member, member).fetchJoin()
                .leftJoin(order.orderItemList, orderItem).fetchJoin()
                .fetch();
    }

}
