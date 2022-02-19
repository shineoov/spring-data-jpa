package shineoov.springdatajpa.domain.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shineoov.springdatajpa.domain.CommonJpaRepository;

import java.util.List;

public interface ItemRepository extends CommonJpaRepository<Item, Long>, ItemCustomRepository {

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

    @Query("SELECT i FROM Item AS i WHERE i.itemName = ?1 ORDER BY i.price DESC")
    List<Item> findByItemNameWithDeclareQueryV1(String itemName);

    @Query("SELECT i FROM Item AS i WHERE i.itemName = :itemName")
    List<Item> findByItemNameWithDeclareQueryV2(@Param("itemName") String name);

    @Query("SELECT i FROM Item AS i WHERE i.itemName = :itemName")
    List<Item> findByItemNameWithDeclareQueryV3(String itemName);

    // LIKE
    List<Item> findByItemNameLike(String itemName);

    @Query("SELECT i FROM Item AS i WHERE i.itemName LIKE :itemName ")
    List<Item> findByItemNameLikeWithDeclareQuery(String itemName);

    // IN
    List<Item> findByPriceIn(List<Integer> ages);

    @Query("SELECT i FROM Item AS i WHERE i.price IN :ages")
    List<Item> findByPriceInWithDeclareQuery(List<Integer> ages);

}
