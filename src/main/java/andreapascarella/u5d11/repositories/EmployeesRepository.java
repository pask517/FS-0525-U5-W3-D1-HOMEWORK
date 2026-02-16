package andreapascarella.u5d11.repositories;

import andreapascarella.u5d11.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Employee> findByEmail(String email);
}
