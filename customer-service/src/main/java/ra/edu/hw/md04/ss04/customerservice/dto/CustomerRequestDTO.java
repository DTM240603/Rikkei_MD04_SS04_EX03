package ra.edu.hw.md04.ss04.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
        @NotBlank(message = "fullName is required") String fullName,
        @NotBlank(message = "email is required") @Email(message = "email must be valid") String email,
        @NotBlank(message = "password is required") String password,
        @NotBlank(message = "address is required") String address
) {
}
