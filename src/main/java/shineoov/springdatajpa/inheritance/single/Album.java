package shineoov.springdatajpa.inheritance.single;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SingleAlbum")
@DiscriminatorValue("A")
public class Album extends Item {

    private String artist;
}
