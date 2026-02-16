package andreapascarella.u5d11.repositories;

import andreapascarella.u5d11.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TravelsRepository extends JpaRepository<Travel, UUID> {

    boolean existsByDestinationAndTravelDate(String destination, LocalDate travelDate);
}
