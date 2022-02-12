package shineoov.springdatajpa.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
@DisplayName("기본 CRUD 사용")
public class BasicCRUDTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void create() {
        Item givenItem = new Item("newItem", 1000);

        Item item = itemRepository.save(givenItem);

        assertAll(
                () -> assertThat(givenItem).isEqualTo(item),
                () -> assertThat(givenItem.getId()).isEqualTo(item.getId())
        );
    }

    @Test
    void read() {
        //given
        Item item = new Item("item", 5000);
        itemRepository.save(item);

        //when
        Optional<Item> optionalItem = itemRepository.findById(item.getId());

        //then
        assertThat(optionalItem.isPresent()).isTrue();
    }

    @Test
    void readList() {
        //given
        itemRepository.save(new Item("AAA", 1000));
        itemRepository.save(new Item("AAA", 2000));

        //when
        List<Item> itemList = itemRepository.findByItemName("AAA");

        //then
        assertThat(itemList.size()).isEqualTo(2);
    }

    @Test
    void readListPage() {
        //given
        IntStream.rangeClosed(1, 50)
                .boxed()
                .forEach((index) -> itemRepository.save(new Item("ITEM" + index, 1000)));

        PageRequest pageRequest = PageRequest.of(1, 10);

        //when
        Page<Item> itemList = itemRepository.findAll(pageRequest);

        //then
        assertAll(
                () -> assertThat(itemList.getTotalElements()).isEqualTo(50),
                () -> assertThat(itemList.getContent().size()).isEqualTo(10)
        );
    }

    @Test
    void update() {
        //given
        Item item = new Item("item", 5000);
        itemRepository.save(item);

        item.setItemName("BBB");
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findById(item.getId()).get();

        //then
        assertThat(findItem.getItemName()).isEqualTo("BBB");
    }

    @Test
    void deleteById() {
        //given
        Item item = new Item("AAA", 1000);
        itemRepository.save(item);

        //when
        itemRepository.delete(item);
        boolean isExists = itemRepository.existsById(item.getId());

        //then
        assertThat(isExists).isFalse();
    }

    @Test
    @Transactional
    void delete() {
        //given
        itemRepository.save(new Item("AAA", 1000));
        itemRepository.save(new Item("AAA", 2000));
        itemRepository.save(new Item("BBB", 1000));
        itemRepository.save(new Item("AAA", 2000));

        //when
        Long deleteCount = itemRepository.deleteByItemName("AAA");

        //then
        assertThat(deleteCount).isEqualTo(3);
    }

    @Test
    void count() {
        //given
        itemRepository.save(new Item("AAA", 1000));
        itemRepository.save(new Item("AAA", 2000));

        //when
        long count = itemRepository.count();

        //then
        assertThat(count).isEqualTo(2);
    }
    
    @Test
    void count2() {
        //given
        itemRepository.save(new Item("AAA", 1000));
        itemRepository.save(new Item("AAA", 2000));
        itemRepository.save(new Item("BBB", 3000));

        //when
        long count = itemRepository.countByItemName("AAA");
        
        //then
        assertThat(count).isEqualTo(2);
    }
    
    @Test
    void exists() {
        //given
        Item item = new Item("AAA", 1000);
        itemRepository.save(item);
        
        //when
        boolean isExists = itemRepository.existsById(item.getId());

        //then
        assertThat(isExists).isTrue();
    }
}
