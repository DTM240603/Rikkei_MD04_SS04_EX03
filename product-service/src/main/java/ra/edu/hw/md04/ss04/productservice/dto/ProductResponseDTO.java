package ra.edu.hw.md04.ss04.productservice.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description
) {
}
