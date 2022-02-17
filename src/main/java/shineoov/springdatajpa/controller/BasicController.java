package shineoov.springdatajpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shineoov.springdatajpa.controller.dto.ItemDto;
import shineoov.springdatajpa.domain.item.Item;

@RestController
public class BasicController {

    @GetMapping("/domain-class-converter/{id}")
    public ItemDto getItem(@PathVariable("id") Item item) {
        return new ItemDto(item);
    }
}
