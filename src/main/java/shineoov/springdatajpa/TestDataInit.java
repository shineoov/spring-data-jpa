package shineoov.springdatajpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shineoov.springdatajpa.domain.Item;
import shineoov.springdatajpa.domain.ItemRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000));
        itemRepository.save(new Item("itemB", 20000));
    }
}
