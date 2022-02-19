package shineoov.springdatajpa.domain.inheritance.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "book_id") // <- 자식 테이블의 기본키 컬럼명 변경
public class Book extends ItemV2 {

    private String author;
    private String isbn;
}
