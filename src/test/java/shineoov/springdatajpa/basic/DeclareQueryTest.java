package shineoov.springdatajpa.basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class DeclareQueryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void list() {
        //given
        String itemName = "itemA";
        Item itemA = new Item(itemName, 1000);
        itemRepository.save(itemA);

        //when
        List<Item> itemList = itemRepository.findByItemNameWithDeclareQueryV3(itemName);

        //then
        assertAll(
                () -> assertThat(itemList.size()).isEqualTo(1),
                () -> assertThat(itemList.get(0).getItemName()).isEqualTo(itemName)
        );
    }

    @Test
    void like() {
        //given
        String itemName = "itemA";
        Item itemA = new Item(itemName, 1000);
        itemRepository.save(itemA);

        //when
        List<Item> itemList = itemRepository.findByItemNameLike("%te%");

        //then
        assertAll(
                () -> assertThat(itemList.size()).isEqualTo(1),
                () -> assertThat(itemList.get(0).getItemName()).isEqualTo(itemName)
        );
    }

    @Test
    void in() {
        //given
        String itemName = "itemA";
        itemRepository.save(new Item(itemName, 1000));
        itemRepository.save(new Item(itemName, 500));
        itemRepository.save(new Item(itemName, 3000));

        //when
        List<Integer> prices = Arrays.asList(500, 1000, 2000);
        List<Item> itemList = itemRepository.findByPriceInWithDeclareQuery(prices);

        //then
        assertAll(
                () -> assertThat(itemList.size()).isEqualTo(2),
                () -> assertThat(itemList.get(0).getItemName()).isEqualTo(itemName)
        );
    }
}
