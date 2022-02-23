package shineoov.springdatajpa.orphan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Commit
@Transactional
@SpringBootTest
public class OrphanTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("고아 객체 삭제")
    void deleteOrphanChildren() {
        //given
        Parent parent1 = new Parent();
        em.persist(parent1);

        Child childA = new Child(parent1);
        Child childB = new Child(parent1);
        em.persist(childA);
        em.persist(childB);

        em.flush();
        em.clear();

        // 고아 객체까지 삭제 - DELETE 3번
        Parent parent2 = em.find(Parent.class, parent1.getId());
        em.remove(parent2);
    }
}
