package ra.edu.hw.md04.ss04.orderservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderRequestDTO(
        @NotNull(message = "customerId is required") Long customerId,
        @NotNull(message = "totalAmount is required") @DecimalMin(value = "0.0", inclusive = false, message = "totalAmount must be greater than 0") BigDecimal totalAmount,
        @NotNull(message = "status is required") String status
) {
}
