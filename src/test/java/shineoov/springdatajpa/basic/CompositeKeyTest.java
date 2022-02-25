package shineoov.springdatajpa.basic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import shineoov.springdatajpa.composite_key.Parent;
import shineoov.springdatajpa.composite_key.ParentId;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertAll;

@Transactional
@Rollback(value = false)
@SpringBootTest
public class CompositeKeyTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("복합키 저장 및 조회")
    void save_and_find() {
        //given
        String firstId = "ID1";
        String secondId = "ID2";

        Parent parent = new Parent();
        parent.setId1(firstId);
        parent.setId2(secondId);
        parent.setName("Oh");

        em.persist(parent);
        em.flush();
        em.clear();

        //when
        ParentId parentId = new ParentId(firstId, secondId);
        Parent findParent = em.find(Parent.class, parentId);

        //then
        assertAll(
                () -> Assertions.assertThat(findParent.getId1()).isEqualTo(firstId),
                () -> Assertions.assertThat(findParent.getId2()).isEqualTo(secondId)
        );
    }
}
