package az.edu.ada.wm2.lab4.controller;

import az.edu.ada.wm2.lab4.model.Product;
import az.edu.ada.wm2.lab4.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product created = productService.createProduct(product);
            return ResponseEntity.status(201).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/filter/expiring")
    public ResponseEntity<List<Product>> filterExpiring(@RequestParam("date") LocalDate date) {
        try {
            return ResponseEntity.ok(productService.getProductsExpiringBefore(date));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> filterByPrice(@RequestParam("min") BigDecimal min,
                                                       @RequestParam("max") BigDecimal max) {
        try {
            return ResponseEntity.ok(productService.getProductsByPriceRange(min, max));
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}