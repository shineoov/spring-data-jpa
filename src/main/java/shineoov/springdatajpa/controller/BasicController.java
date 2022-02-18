package shineoov.springdatajpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shineoov.springdatajpa.controller.dto.ItemDto;
import shineoov.springdatajpa.domain.item.Item;
import shineoov.springdatajpa.domain.item.ItemRepository;

import java.util.List;

@RestController
public class BasicController {

    private final ItemRepository itemRepository;

    public BasicController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/domain-class-converter/{id}")
    public ItemDto getItem(@PathVariable("id") Item item) {
        return new ItemDto(item);
    }

    @GetMapping("/pageable")
    public Page<Item> itemList(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
}
