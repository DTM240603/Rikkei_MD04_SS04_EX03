package ra.edu.hw.md04.ss04.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.edu.hw.md04.ss04.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
