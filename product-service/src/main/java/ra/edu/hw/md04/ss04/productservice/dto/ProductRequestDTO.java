package ra.edu.hw.md04.ss04.productservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "name is required") String name,
        @NotNull(message = "price is required") @DecimalMin(value = "-500.00", message = "price must be greater than or equal to -500") BigDecimal price,
        @NotNull(message = "stockQuantity is required") @Min(value = 0, message = "stockQuantity must be greater than or equal to 0") Integer stockQuantity,
        String description
) {
}
