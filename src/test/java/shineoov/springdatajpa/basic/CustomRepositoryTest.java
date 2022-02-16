package shineoov.springdatajpa.basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        IntStream.rangeClosed(1, 30)
                .boxed()
                .forEach((index) -> itemRepository.save(new Item("item" + index, 500 * index)));
    }

    @Test
    @DisplayName("전체 조회")
    void findAll() {
        List<Item> itemList = itemRepository.findCustomAll();
        assertThat(itemList.size()).isEqualTo(30);
    }

    @Test
    @DisplayName("ID 로 조회")
    void findById() {
        //given
        Item newItem = new Item("newItem", 500);
        itemRepository.save(newItem);

        //when
        Item item = itemRepository.findCustomById(newItem.getId())
                .orElseThrow(() -> {
                    throw new RuntimeException();
                });

        //then
        assertThat(item.getId()).isEqualTo(newItem.getId());
    }
}
