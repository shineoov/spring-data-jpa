package shineoov.springdatajpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CustomBasicRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Rollback(value = false)
    void contains_true() {
        //given
        Item itemA = new Item("itemA", 1000);
        itemRepository.save(itemA);

        //when
        boolean secondActual = itemRepository.contains(itemA);
        //then
        assertThat(secondActual).isTrue();
    }

    @Test
    void contains_false() {
        //given
        Item itemA = new Item("itemA", 1000);
        //when
        boolean firstActual = itemRepository.contains(itemA);
        //then
        assertThat(firstActual).isFalse();
    }
}
