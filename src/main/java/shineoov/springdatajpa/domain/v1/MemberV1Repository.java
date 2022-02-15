package shineoov.springdatajpa.domain.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberV1Repository extends JpaRepository<MemberV1, Long> {

    @Query("select m from MemberV1 m join m.team t where m.username = ?1")
    MemberV1 findByUsername(String username);

}
