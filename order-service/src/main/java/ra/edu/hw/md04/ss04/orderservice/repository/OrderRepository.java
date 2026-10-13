package ra.edu.hw.md04.ss04.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.hw.md04.ss04.orderservice.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
