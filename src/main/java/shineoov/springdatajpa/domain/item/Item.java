package shineoov.springdatajpa.domain.item;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString(of = {"id", "itemName", "price"})
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String itemName;

    private Integer price;

    protected Item() {
    }

    public Item(String itemName, Integer price) {
        this.itemName = itemName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
