package shineoov.springdatajpa.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName);

    Page<Item> findAll(Pageable pageable);

    Slice<Item> findSliceBy(Pageable pageable);

    Long countByItemName(String itemName);

    Long deleteByItemName(String itemName);

    List<Item> findByItemNameAndPrice(String itemName, Integer price);

    List<Item> findByItemNameOrPrice(String itemName, Integer price);

    List<Item> findByItemNameIgnoreCase(String itemName);

    List<Item> findByItemNameOrderByPriceDesc(String itemName);

    List<Item> findByItemNameOrderByPriceAsc(String itemName);
}
