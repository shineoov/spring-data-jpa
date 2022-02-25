package shineoov.springdatajpa.inheritance.per;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "PerAlbum")
@Table(name = "per_album")
public class Album extends ItemV3 {

    private String artist;
}
