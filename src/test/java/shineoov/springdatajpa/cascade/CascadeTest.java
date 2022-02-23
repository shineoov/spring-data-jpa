package shineoov.springdatajpa.cascade;

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
public class CascadeTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("영속성 전이 저장 및 영속성 전이 삭제")
    void saveCascadeAndRemove() {
        // 저장 CascadeType.PERSIST
        Parent parent1 = new Parent();
        Child child1 = new Child();
        Child child2 = new Child();
        child1.setParent(parent1);
        child2.setParent(parent1);
        parent1.getChildren().add(child1);
        parent1.getChildren().add(child2);

        em.persist(parent1);
        em.flush();
        em.clear();

        // 삭제 CascadeType.REMOVE
        Parent parent2 = em.find(Parent.class, parent1.getId());
        em.remove(parent2);
    }
}
