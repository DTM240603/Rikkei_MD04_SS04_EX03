package ra.edu.hw.md04.ss04.customerservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.hw.md04.ss04.customerservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
