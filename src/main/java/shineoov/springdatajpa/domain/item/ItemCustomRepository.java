package shineoov.springdatajpa.domain.item;

import java.util.List;
import java.util.Optional;

public interface ItemCustomRepository {

    List<Item> findCustomAll();

    Optional<Item> findCustomById(Long id);
}
