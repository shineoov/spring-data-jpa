package shineoov.springdatajpa.inheritance.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SingleBook")
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;
    private String isbn;
}
