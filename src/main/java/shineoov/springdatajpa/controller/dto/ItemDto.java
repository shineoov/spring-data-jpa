package shineoov.springdatajpa.controller.dto;

import lombok.Data;
import shineoov.springdatajpa.domain.item.Item;

@Data
public class ItemDto {
    private String name;
    private Integer price;

    public ItemDto(Item item) {
        if(item == null) {
            throw new IllegalStateException("Empty Item");
        }
        this.name = item.getItemName();
        this.price = item.getPrice();
    }
}
