package shineoov.springdatajpa.domain.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamV1Repository extends JpaRepository<TeamV1, Long> {

    Optional<TeamV1> findByName(String name);
}
