package shineoov.springdatajpa.domain.item;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final EntityManager em;

    public ItemCustomRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Item> findCustomAll() {
        return em.createQuery("SELECT i FROM Item AS i ", Item.class)
                .getResultList();
    }

    @Override
    public Optional<Item> findCustomById(Long id) {
        Item item = em.createQuery("SELECT i FROM Item AS i WHERE i.id = :id", Item.class)
                .setParameter("id", id)
                .getSingleResult();
        return Optional.ofNullable(item);
    }
}
