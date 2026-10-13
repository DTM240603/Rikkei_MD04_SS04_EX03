package ra.edu.hw.md04.ss04.productservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ra.edu.hw.md04.ss04.productservice.dto.ProductRequestDTO;
import ra.edu.hw.md04.ss04.productservice.dto.ProductResponseDTO;
import ra.edu.hw.md04.ss04.productservice.entity.Product;
import ra.edu.hw.md04.ss04.productservice.exception.ResourceNotFoundException;
import ra.edu.hw.md04.ss04.productservice.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO create(ProductRequestDTO request) {
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setDescription(request.description());

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return toResponse(product);
    }

    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    private ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription()
        );
    }
}
