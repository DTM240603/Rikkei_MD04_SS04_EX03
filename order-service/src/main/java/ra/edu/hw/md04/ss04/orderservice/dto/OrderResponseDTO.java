package ra.edu.hw.md04.ss04.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status
) {
}
