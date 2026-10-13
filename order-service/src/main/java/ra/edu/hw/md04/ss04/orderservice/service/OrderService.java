package ra.edu.hw.md04.ss04.orderservice.service;

import java.time.LocalDateTime;
import java.net.URI;
import java.util.List;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ra.edu.hw.md04.ss04.orderservice.dto.OrderRequestDTO;
import ra.edu.hw.md04.ss04.orderservice.dto.OrderResponseDTO;
import ra.edu.hw.md04.ss04.orderservice.dto.ProductResponseDTO;
import ra.edu.hw.md04.ss04.orderservice.entity.Order;
import ra.edu.hw.md04.ss04.orderservice.exception.ResourceNotFoundException;
import ra.edu.hw.md04.ss04.orderservice.exception.ServiceUnavailableException;
import ra.edu.hw.md04.ss04.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public OrderService(OrderRepository orderRepository, DiscoveryClient discoveryClient) {
        this.orderRepository = orderRepository;
        this.discoveryClient = discoveryClient;
        this.restClient = RestClient.create();
    }

    public ProductResponseDTO getProductFromProductService(Long productId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("product-service");
        if (instances == null || instances.isEmpty()) {
            throw new ServiceUnavailableException("PRODUCT-SERVICE is currently unavailable");
        }

        URI productUri = instances.get(0).getUri().resolve("/api/v1/products/" + productId);
        ProductResponseDTO product = restClient.get()
                .uri(productUri)
                .retrieve()
                .body(ProductResponseDTO.class);

        if (product == null) {
            throw new IllegalStateException("PRODUCT-SERVICE returned an empty response");
        }
        return product;
    }

    public OrderResponseDTO create(OrderRequestDTO request) {
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(request.totalAmount());
        order.setStatus(request.status());

        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream().map(this::toResponse).toList();
    }

    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return toResponse(order);
    }

    public void deleteById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    private OrderResponseDTO toResponse(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getCustomerId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }
}
