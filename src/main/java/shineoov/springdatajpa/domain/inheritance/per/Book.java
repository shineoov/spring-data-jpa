package shineoov.springdatajpa.domain.inheritance.per;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PerBook")
@Table(name = "per_book")
public class Book extends ItemV3 {

    private String author;
    private String isbn;
}
