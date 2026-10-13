package ra.edu.hw.md04.ss04.customerservice.dto;

public record CustomerResponseDTO(
        Long id,
        String fullName,
        String email,
        String address
) {
}
