package shineoov.springdatajpa.jpashop.repository;

import org.springframework.stereotype.Repository;
import shineoov.springdatajpa.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from ShopMember m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from ShopMember m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
