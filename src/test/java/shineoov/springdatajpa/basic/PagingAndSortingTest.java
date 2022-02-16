package shineoov.springdatajpa.basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class PagingAndSortingTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("Slice - 카운트 쿼리 X ")
    void slice() {
        //given
        generateItems();

        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        Pageable pageRequest = PageRequest.of(0, 10, sort);

        //when
        Slice<Item> itemList = itemRepository.findSliceBy(pageRequest);

        //then
        assertAll(
                () -> assertThat(itemList.getContent().size()).isEqualTo(10),
                () -> assertThat(itemList.getSize()).isEqualTo(10),
                () -> assertThat(itemList.getNumber()).isEqualTo(0),
                () -> assertThat(itemList.getPageable()).isEqualTo(pageRequest),
                () -> assertThat(itemList.getSort()).isEqualTo(sort),
                () -> assertThat(itemList.isFirst()).isTrue(),
                () -> assertThat(itemList.isLast()).isFalse(),
                () -> assertThat(itemList.hasNext()).isTrue(),
                () -> assertThat(itemList.hasPrevious()).isFalse()
        );
    }

    @Test
    @DisplayName("PAGE - 카운트 쿼리 포함  (Slice Child) ")
    void page() {
        //given
        generateItems();

        Sort sort = Sort.by(Sort.Direction.ASC, "price");
        PageRequest pageRequest = PageRequest.of(0, 10, sort);

        //when
        Page<Item> itemList = itemRepository.findAll(pageRequest);

        //then
        assertThat(itemList.getTotalElements()).isEqualTo(20);
        assertThat(itemList.getTotalPages()).isEqualTo(2);
    }

    private void generateItems() {
        IntStream.rangeClosed(1, 20)
                .boxed()
                .forEach(index -> itemRepository.save(new Item("item" + index, index * 500)));
    }
}
