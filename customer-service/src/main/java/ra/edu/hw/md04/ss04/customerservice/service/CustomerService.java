package ra.edu.hw.md04.ss04.customerservice.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ra.edu.hw.md04.ss04.customerservice.dto.CustomerRequestDTO;
import ra.edu.hw.md04.ss04.customerservice.dto.CustomerResponseDTO;
import ra.edu.hw.md04.ss04.customerservice.dto.LoginRequestDTO;
import ra.edu.hw.md04.ss04.customerservice.entity.Customer;
import ra.edu.hw.md04.ss04.customerservice.exception.ResourceNotFoundException;
import ra.edu.hw.md04.ss04.customerservice.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponseDTO register(CustomerRequestDTO request) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(request.email());
        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = new Customer();
        customer.setFullName(request.fullName());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        customer.setAddress(request.address());

        Customer saved = customerRepository.save(customer);
        return toResponse(saved);
    }

    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CustomerResponseDTO findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return toResponse(customer);
    }

    public CustomerResponseDTO login(LoginRequestDTO request) {
        Customer customer = customerRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email or password incorrect"));

        if (!customer.getPassword().equals(request.password())) {
            throw new IllegalArgumentException("Email or password incorrect");
        }

        return toResponse(customer);
    }

    private CustomerResponseDTO toResponse(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
