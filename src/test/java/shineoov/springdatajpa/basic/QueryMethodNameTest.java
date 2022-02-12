package shineoov.springdatajpa.basic;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shineoov.springdatajpa.domain.Item;
import shineoov.springdatajpa.domain.ItemRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@SpringBootTest
@DisplayName("메소드 이름으로 쿼리 생성")
public class QueryMethodNameTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void andQuery() {
        //given
        Item itemA = new Item("AAA", 1000);
        Item itemB = new Item("AAA", 2000);

        itemRepository.saveAll(List.of(itemA, itemB));

        //when
        List<Item> itemList = itemRepository.findByItemNameAndPrice("AAA", 2000);
        Item actualItem = itemList.get(0);

        //then
        assertAll(
                () -> assertThat(itemList.size()).isEqualTo(1),
                () -> assertThat(actualItem.getId()).isEqualTo(itemB.getId())
        );
    }

    @Test
    void orQuery() {
        //given
        Item itemA = new Item("AAA", 1000);
        Item itemB = new Item("BBB", 2000);
        itemRepository.saveAll(List.of(itemA, itemB));

        //when
        int size = itemRepository.findByItemNameOrPrice("AAA", 2000).size();

        //then
        assertThat(size).isEqualTo(2);
    }

    @Test
    void ignoreCase() {
        //given
        Item itemA = new Item("AAA", 1000);
        itemRepository.save(itemA);

        //when
        int size = itemRepository.findByItemNameIgnoreCase("aaa").size();

        //then
        assertThat(size).isEqualTo(1);
    }

    @Test
    void orderByDesc() {
        //given
        Item itemA = new Item("AAA", 1000);
        Item itemB = new Item("AAA", 2000);
        itemRepository.saveAll(List.of(itemA, itemB));

        //when
        Item item = itemRepository.findByItemNameOrderByPriceDesc("AAA").get(0);

        //then
        assertThat(item.getId()).isEqualTo(itemB.getId());
    }

    @Test
    void orderByAsc() {
        //given
        Item itemA = new Item("AAA", 1000);
        Item itemB = new Item("AAA", 2000);
        itemRepository.saveAll(List.of(itemA, itemB));

        //when
        Item item = itemRepository.findByItemNameOrderByPriceAsc("AAA").get(0);

        //then
        assertThat(item.getId()).isEqualTo(itemA.getId());
    }
}
