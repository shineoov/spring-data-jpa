package shineoov.springdatajpa.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName);

    Page<Item> findAll(Pageable pageable);

    Long countByItemName(String itemName);

    Long deleteByItemName(String itemName);

}
