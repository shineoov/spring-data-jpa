package shineoov.springdatajpa.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "ShopBook")
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;

    private String isbn;

}
